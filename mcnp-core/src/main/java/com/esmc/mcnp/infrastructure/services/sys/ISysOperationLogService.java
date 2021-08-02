package com.kreatech.api.module.sys.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kreatech.data.entity.sys.SysOperationLog;

public interface ISysOperationLogService {

    SysOperationLog save(SysOperationLog sysOperationLog);

    Page<SysOperationLog> findPage(Map<String, Object> condition, Pageable pageable);

    void deleteById(Long id);
}
