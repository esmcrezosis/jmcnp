package com.esmc.mcnp.services.security;

import com.esmc.mcnp.dto.projections.GroupeRolesVO;
import com.esmc.mcnp.model.security.EuGroupeRoles;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EuGroupeRolesService extends CrudService<EuGroupeRoles, Integer> {
    @Async
    CompletableFuture<List<EuGroupeRoles>> loadAllAsync();

    List<GroupeRolesVO> getAll();
}
