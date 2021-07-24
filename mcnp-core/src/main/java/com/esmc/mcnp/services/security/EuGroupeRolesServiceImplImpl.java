package com.esmc.mcnp.services.security;

import com.esmc.mcnp.dto.projections.GroupeRolesVO;
import com.esmc.mcnp.model.security.EuGroupeRoles;
import com.esmc.mcnp.repositories.security.EuGroupeRoleRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
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
