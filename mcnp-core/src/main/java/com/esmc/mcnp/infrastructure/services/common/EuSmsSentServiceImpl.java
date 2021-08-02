package com.esmc.mcnp.infrastructure.services.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.common.EuSmsSentRepository;
import com.esmc.mcnp.domain.entity.sms.EuSmsSent;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euSmsSentService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuSmsSentServiceImpl extends BaseServiceImpl<EuSmsSent, Long> implements EuSmsSentService{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private EuSmsSentRepository smsSentRepo;

	@Override
	protected BaseRepository<EuSmsSent, Long> getRepository() {
		return smsSentRepo;
	}

	@Override
	public Long findMaxInsertedIdSmsSent() {
		return smsSentRepo.findMaxInsertedIdSmsSent();
	}
	
	
}
