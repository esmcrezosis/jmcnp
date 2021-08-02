package com.esmc.mcnp.dao.repository.sys;

import com.esmc.mcnp.domain.entity.sys.SysOperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SysOperationLogRepository extends JpaRepository<SysOperationLog, Long>, JpaSpecificationExecutor<SysOperationLog>, QuerydslPredicateExecutor<SysOperationLog> {

}
