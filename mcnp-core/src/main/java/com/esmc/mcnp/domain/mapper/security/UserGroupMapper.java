package com.esmc.mcnp.domain.mapper.security;

import com.esmc.mcnp.domain.dto.security.UserGroupDTO;
import com.esmc.mcnp.domain.entity.security.EuUserGroup;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserGroupMapper {
    UserGroupDTO fromEuUserGroup(EuUserGroup userGroup);

    List<UserGroupDTO> fromEuUserGroupList(List<EuUserGroup> userGroups);
}
