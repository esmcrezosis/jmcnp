package com.esmc.mcnp.util;

import com.esmc.mcnp.model.security.EuUserRolesPermission;

import java.util.List;

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
