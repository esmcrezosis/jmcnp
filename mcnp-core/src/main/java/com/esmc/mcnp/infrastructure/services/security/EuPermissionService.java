package com.esmc.mcnp.services.security;

import com.esmc.mcnp.model.security.EuPermission;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EuPermissionService extends CrudService<EuPermission, Integer> {
    @Async
    CompletableFuture<List<EuPermission>> loadAllAsync();
}
