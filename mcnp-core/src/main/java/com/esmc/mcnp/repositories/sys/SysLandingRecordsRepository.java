package com.esmc.mcnp.repositories.sys;

import com.esmc.mcnp.model.sys.SysLandingRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * sys_landing_records Repository
*/ 
@Repository 
public interface SysLandingRecordsRepository extends JpaRepository<SysLandingRecords, Long> {
}

