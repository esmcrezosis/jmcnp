package com.esmc.mcnp.util;

import java.util.List;

import com.esmc.mcnp.domain.entity.security.EuUserRolesPermission;

public class SecurityUtil {

    public static boolean isUserInRole(List<EuUserRolesPermission> rolesPermissions, String role) {
        if (!rolesPermissions.isEmpty()) {
            for (EuUserRolesPermission r : rolesPermissions) {
                if (r.getEuRole().getCodeRoles().equalsIgnoreCase(role)) {
                    return true;
                }
            }
        }
        return false;
    }
}
