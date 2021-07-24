package com.esmc.mcnp.services.common;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.sms.EuSms;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.common.EuSmsRepository;


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
