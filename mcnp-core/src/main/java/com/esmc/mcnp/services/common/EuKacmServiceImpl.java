package com.esmc.mcnp.services.common;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.ksu.EuKacm;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.common.EuKacmRepository;

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
