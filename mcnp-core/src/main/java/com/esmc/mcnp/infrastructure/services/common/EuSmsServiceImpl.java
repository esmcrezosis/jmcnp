package com.esmc.mcnp.infrastructure.services.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.common.EuSmsRepository;
import com.esmc.mcnp.domain.entity.sms.EuSms;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;


@Service("euSmsService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuSmsServiceImpl extends BaseServiceImpl<EuSms, Long> implements EuSmsService{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EuSmsRepository smsRepo;
	
	
	
	
	@Override
	protected BaseRepository<EuSms, Long> getRepository() {
		return smsRepo;
	}

	
	@Override
	public Long getLastSmsInserted() {
		return smsRepo.getLastSmsInserted();
	}
	

}
