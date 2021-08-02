package com.esmc.mcnp.infrastructure.services.sys;

import com.esmc.mcnp.domain.entity.sys.SysOperationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ISysOperationLogService {

    SysOperationLog save(SysOperationLog sysOperationLog);

    Page<SysOperationLog> findPage(Map<String, Object> condition, Pageable pageable);

    void deleteById(Long id);
}
