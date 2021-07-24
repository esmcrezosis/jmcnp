package com.esmc.mcnp.mapper.security;

import com.esmc.mcnp.dto.security.GroupeRoles;
import com.esmc.mcnp.model.security.EuGroupeRoles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupeRolesMapper {
    @Mapping(source = "id", target = "idGroupeRoles")
    EuGroupeRoles toEuGroupeRoles(GroupeRoles groupeRoles);

    @Mapping(source = "idGroupeRoles", target = "id")
    GroupeRoles fromEuGroupeRoles(EuGroupeRoles groupeRoles);

    List<GroupeRoles> fromEuGroupeRolesList(List<EuGroupeRoles> euGroupeRoles);
}
