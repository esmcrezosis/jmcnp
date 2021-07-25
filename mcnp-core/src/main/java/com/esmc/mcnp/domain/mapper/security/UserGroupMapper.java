package com.esmc.mcnp.mapper.security;

import com.esmc.mcnp.dto.security.UserGroupDTO;
import com.esmc.mcnp.model.security.EuUserGroup;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserGroupMapper {
    UserGroupDTO fromEuUserGroup(EuUserGroup userGroup);

    List<UserGroupDTO> fromEuUserGroupList(List<EuUserGroup> userGroups);
}
