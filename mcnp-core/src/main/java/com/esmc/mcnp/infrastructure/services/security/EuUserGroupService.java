package com.esmc.mcnp.services.security;

import com.esmc.mcnp.dto.projections.UserGroupVO;
import com.esmc.mcnp.dto.security.UserGroupDTO;
import com.esmc.mcnp.model.security.EuUserGroup;
import com.esmc.mcnp.services.base.CrudService;

import java.util.List;

public interface EuUserGroupService extends CrudService<EuUserGroup, String> {

    List<UserGroupDTO> loadAll();

    List<UserGroupVO> getAll();
}
