package com.esmc.mcnp.core.utils;

import com.esmc.mcnp.UserSecurity;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserSecurity getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserSecurity) {
            return ((UserSecurity) principal);
        }
        return null;
    }
}
