package com.esmc.mcnp.infrastructure.services.security;

import com.esmc.mcnp.domain.dto.projections.UserGroupVO;
import com.esmc.mcnp.domain.dto.security.UserGroupDTO;
import com.esmc.mcnp.domain.entity.security.EuUserGroup;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import java.util.List;

public interface EuUserGroupService extends CrudService<EuUserGroup, String> {

    List<UserGroupDTO> loadAll();

    List<UserGroupVO> getAll();
}
