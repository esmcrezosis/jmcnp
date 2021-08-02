package com.esmc.mcnp.infrastructure.services.security;

import com.esmc.mcnp.domain.dto.projections.RolesVO;
import com.esmc.mcnp.domain.entity.security.EuRoles;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EuRolesService extends CrudService<EuRoles, Integer> {
    List<EuRoles> findByGroupeRole(Integer idGroupeRole);

    @Async
    CompletableFuture<List<EuRoles>> loadAllAsync();

    EuRoles getByCode(String codeRole);

    List<RolesVO> getAll();
}
