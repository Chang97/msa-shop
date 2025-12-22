package com.msashop.auth.infrastructure.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.msashop.auth.infrastructure.config.InternalAccessProperties;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InternalAccessFilter extends OncePerRequestFilter {

    private final InternalAccessProperties internalAccessProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String contextPath = request.getContextPath() == null ? "" : request.getContextPath();
        String internalPath = contextPath + "/internal";
        return !request.getRequestURI().startsWith(internalPath);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String expectedSecret = internalAccessProperties.getServiceSecret();
        if (!StringUtils.hasText(expectedSecret)) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal access secret is not configured.");
            return;
        }
        String headerName = internalAccessProperties.getHeaderName();
        String provided = request.getHeader(headerName);
        if (!expectedSecret.equals(provided)) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Forbidden internal access.");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
