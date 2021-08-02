package com.esmc.mcnp.dao.repository.security;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.dto.projections.RolesVO;
import com.esmc.mcnp.domain.entity.security.EuRoles;

import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EuRolesRepository extends BaseRepository<EuRoles, Integer> {
    List<EuRoles> findByGroupeRoles_IdGroupeRoles(Integer id);

    @Async
    @Query("select r from EuRoles  r left join fetch r.groupeRoles")
    CompletableFuture<List<EuRoles>> loadAllAsync();

    EuRoles getByCodeRoles(String codeRole);

    List<RolesVO> getAllBy();
}
