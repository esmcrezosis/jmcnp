package com.esmc.mcnp.dao.repository.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmc.mcnp.domain.entity.security.DeviceMetadata;

public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {

    List<DeviceMetadata> findByUserId(Long userId);
}
