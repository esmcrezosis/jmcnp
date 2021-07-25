package com.esmc.mcnp.services.security;

import com.esmc.mcnp.dto.projections.UserGroupVO;
import com.esmc.mcnp.dto.security.UserGroupDTO;
import com.esmc.mcnp.model.security.EuUserGroup;
import com.esmc.mcnp.repositories.security.EuUserGroupRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
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
