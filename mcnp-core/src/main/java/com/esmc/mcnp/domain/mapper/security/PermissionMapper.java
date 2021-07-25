package com.esmc.mcnp.mapper.security;

import com.esmc.mcnp.dto.security.Permission;
import com.esmc.mcnp.model.security.EuPermission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    @Mapping(source = "idPermissions", target = "id")
    Permission fromEuPermission(EuPermission permission);

    @Mapping(source = "id", target = "idPermissions")
    EuPermission toEuPermission(Permission permission);

    List<Permission> fromEuPermissionList(List<EuPermission> permissions);
}
