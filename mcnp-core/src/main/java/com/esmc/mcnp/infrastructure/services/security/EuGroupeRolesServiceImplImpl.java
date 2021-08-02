package com.esmc.mcnp.infrastructure.services.security;

import com.esmc.mcnp.dao.repository.security.EuGroupeRoleRepository;
import com.esmc.mcnp.domain.dto.projections.GroupeRolesVO;
import com.esmc.mcnp.domain.entity.security.EuGroupeRoles;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EuGroupeRolesServiceImplImpl extends CrudServiceImpl<EuGroupeRoles, Integer> implements EuGroupeRolesService {

    private EuGroupeRoleRepository groupeRoleRepository;
    protected EuGroupeRolesServiceImplImpl(EuGroupeRoleRepository groupeRoleRepository) {
        super(groupeRoleRepository);
        this.groupeRoleRepository = groupeRoleRepository;
    }

    @Override
    public CompletableFuture<List<EuGroupeRoles>> loadAllAsync() {
        return groupeRoleRepository.loadAllAsync();
    }

    @Override
    public List<GroupeRolesVO> getAll() {
        return groupeRoleRepository.getAllBy();
    }
}
