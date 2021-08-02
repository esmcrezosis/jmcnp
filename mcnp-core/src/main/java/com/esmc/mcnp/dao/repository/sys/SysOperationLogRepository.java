package com.kreatech.api.module.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.kreatech.data.entity.sys.SysOperationLog;

public interface SysOperationLogRepository extends JpaRepository<SysOperationLog, Long>, JpaSpecificationExecutor<SysOperationLog>, QuerydslPredicateExecutor<SysOperationLog> {

}
