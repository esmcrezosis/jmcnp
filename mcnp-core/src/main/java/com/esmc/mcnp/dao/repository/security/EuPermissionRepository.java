package com.esmc.mcnp.repositories.security;

import com.esmc.mcnp.model.security.EuPermission;
import com.esmc.mcnp.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EuPermissionRepository extends BaseRepository<EuPermission, Integer> {
    @Async
    @Query("select g from EuPermission g")
    CompletableFuture<List<EuPermission>> loadAllAsync();
}
