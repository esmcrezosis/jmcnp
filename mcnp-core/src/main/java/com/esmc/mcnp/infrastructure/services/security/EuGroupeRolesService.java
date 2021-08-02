package com.esmc.mcnp.infrastructure.services.security;

import com.esmc.mcnp.domain.dto.projections.GroupeRolesVO;
import com.esmc.mcnp.domain.entity.security.EuGroupeRoles;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EuGroupeRolesService extends CrudService<EuGroupeRoles, Integer> {
    @Async
    CompletableFuture<List<EuGroupeRoles>> loadAllAsync();

    List<GroupeRolesVO> getAll();
}
