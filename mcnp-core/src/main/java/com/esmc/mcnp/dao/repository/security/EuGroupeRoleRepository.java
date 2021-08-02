package com.esmc.mcnp.dao.repository.security;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.dto.projections.GroupeRolesVO;
import com.esmc.mcnp.domain.entity.security.EuGroupeRoles;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EuGroupeRoleRepository extends BaseRepository<EuGroupeRoles, Integer> {

    @Async
    @Query("select g from EuGroupeRoles g")
    CompletableFuture<List<EuGroupeRoles>> loadAllAsync();

    List<GroupeRolesVO> getAllBy();
}
