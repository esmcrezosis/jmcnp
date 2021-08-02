package com.esmc.mcnp.infrastructure.services.security;

import com.esmc.mcnp.dao.repository.security.EuUserGroupRepository;
import com.esmc.mcnp.domain.dto.projections.UserGroupVO;
import com.esmc.mcnp.domain.dto.security.UserGroupDTO;
import com.esmc.mcnp.domain.entity.security.EuUserGroup;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EuUserGroupServiceImplImpl extends CrudServiceImpl<EuUserGroup, String> implements EuUserGroupService {

    private EuUserGroupRepository userGroupRepository;
    protected EuUserGroupServiceImplImpl(EuUserGroupRepository userGroupRepository) {
        super(userGroupRepository);
        this.userGroupRepository = userGroupRepository;
    }

    @Override
    public List<UserGroupDTO> loadAll() {
        return userGroupRepository.loadAll();
    }

    @Override
    public List<UserGroupVO> getAll() {
        return userGroupRepository.getAllBy();
    }
}
