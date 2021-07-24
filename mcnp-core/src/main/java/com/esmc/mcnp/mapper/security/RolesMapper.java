package com.esmc.mcnp.mapper.security;

import com.esmc.mcnp.dto.security.Roles;
import com.esmc.mcnp.model.security.EuRoles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolesMapper {

    @Mapping(source = "roleParent", target = "parent")
    @Mapping(source = "id", target = "idRoles")
    EuRoles toEuroles(Roles roles);

    @Mapping(source = "parent", target = "roleParent")
    @Mapping(source = "idRoles", target = "id")
    Roles fromEuRoles(EuRoles roles);

    List<Roles> fromEuRolesList(List<EuRoles> euRolesList);
}
