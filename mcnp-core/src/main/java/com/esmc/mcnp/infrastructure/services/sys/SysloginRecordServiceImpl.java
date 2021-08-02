package com.esmc.mcnp.infrastructure.services.sys;

import com.esmc.mcnp.dao.repository.sys.SysLoginRecordRepository;
import com.esmc.mcnp.domain.entity.sys.SysLoginRecord;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysloginRecordServiceImpl extends CrudServiceImpl<SysLoginRecord, Long> implements ISysLoginRecordService {

	private final SysLoginRecordRepository sysLoginRecordRepository;

	protected SysloginRecordServiceImpl(SysLoginRecordRepository sysLoginRecordRepository) {
		super(sysLoginRecordRepository);
		this.sysLoginRecordRepository = sysLoginRecordRepository;
	}

}
