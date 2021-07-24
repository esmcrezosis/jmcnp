package com.esmc.mcnp.repositories.security;

import com.esmc.mcnp.dto.projections.GroupeRolesVO;
import com.esmc.mcnp.model.security.EuGroupeRoles;
import com.esmc.mcnp.repositories.base.BaseRepository;
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
