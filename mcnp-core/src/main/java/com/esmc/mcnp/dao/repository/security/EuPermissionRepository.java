package com.esmc.mcnp.dao.repository.security;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.security.EuPermission;

import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EuPermissionRepository extends BaseRepository<EuPermission, Integer> {
    @Async
    @Query("select g from EuPermission g")
    CompletableFuture<List<EuPermission>> loadAllAsync();
}
