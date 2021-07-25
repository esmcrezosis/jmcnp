package com.esmc.mcnp.repositories.sys;

import com.esmc.mcnp.model.sys.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * sys_log Repository
*/ 
@Repository 
public interface SysLogRepository extends JpaRepository<SysLog, Long> {
}

