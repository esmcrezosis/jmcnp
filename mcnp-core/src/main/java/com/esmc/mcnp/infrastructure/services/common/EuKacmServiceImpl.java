package com.esmc.mcnp.infrastructure.services.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.common.EuKacmRepository;
import com.esmc.mcnp.domain.entity.ksu.EuKacm;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euKacmService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuKacmServiceImpl extends BaseServiceImpl<EuKacm, Long> implements EuKacmService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuKacmRepository kacmRepository;

	@Override
	protected BaseRepository<EuKacm, Long> getRepository() {
		return kacmRepository;
	}

}
