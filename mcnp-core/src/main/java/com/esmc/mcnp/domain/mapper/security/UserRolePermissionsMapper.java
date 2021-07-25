package com.esmc.mcnp.mapper.security;

import com.esmc.mcnp.dto.security.UserPermission;
import com.esmc.mcnp.model.security.EuUserRolesPermission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRolePermissionsMapper {
    EuUserRolesPermission toEuUserRolesPermission(UserPermission userPermission);

    UserPermission fromEuUserRolesPermission(EuUserRolesPermission userRolesPermission);
}
