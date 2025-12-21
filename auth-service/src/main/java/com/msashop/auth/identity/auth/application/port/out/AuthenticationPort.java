package com.msashop.auth.identity.auth.application.port.out;

import com.msashop.auth.infrastructure.security.userdetails.UserPrincipal;

public interface AuthenticationPort {
    UserPrincipal authenticate(String loginId, String rawPassword);
}
