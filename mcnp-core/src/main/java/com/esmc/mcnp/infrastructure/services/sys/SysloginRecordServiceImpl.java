package com.kreatech.api.module.sys.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kreatech.api.module.base.service.BaseServiceImpl;
import com.kreatech.api.module.sys.repository.SysLoginRecordRepository;
import com.kreatech.api.module.sys.service.ISysLoginRecordService;
import com.kreatech.data.entity.sys.SysLoginRecord;

@Service
@Transactional
public class SysloginRecordServiceImpl extends BaseServiceImpl<SysLoginRecord, Long> implements ISysLoginRecordService {

	private final SysLoginRecordRepository sysLoginRecordRepository;

	protected SysloginRecordServiceImpl(SysLoginRecordRepository sysLoginRecordRepository) {
		super(sysLoginRecordRepository);
		this.sysLoginRecordRepository = sysLoginRecordRepository;
	}

}
