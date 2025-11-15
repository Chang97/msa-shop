package com.msashop.auth.contexts.identity.auth.application.port.out;

import com.msashop.auth.platform.security.userdetails.UserPrincipal;

public interface AuthenticationPort {
    UserPrincipal authenticate(String loginId, String rawPassword);
}
