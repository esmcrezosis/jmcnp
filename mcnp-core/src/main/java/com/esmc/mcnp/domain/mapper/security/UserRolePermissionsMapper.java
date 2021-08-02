package com.esmc.mcnp.domain.mapper.security;

import com.esmc.mcnp.domain.dto.security.UserPermission;
import com.esmc.mcnp.domain.entity.security.EuUserRolesPermission;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRolePermissionsMapper {
    EuUserRolesPermission toEuUserRolesPermission(UserPermission userPermission);

    UserPermission fromEuUserRolesPermission(EuUserRolesPermission userRolesPermission);
}
