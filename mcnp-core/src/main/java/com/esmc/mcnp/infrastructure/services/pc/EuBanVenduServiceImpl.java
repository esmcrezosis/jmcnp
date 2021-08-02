package com.esmc.mcnp.infrastructure.services.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.pc.EuBanVenduRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuBanVendu;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuBanVenduServiceImpl extends BaseServiceImpl<EuBanVendu, Long> implements EuBanVenduService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuBanVenduRepository banVenduRepo;

	@Override
	protected BaseRepository<EuBanVendu, Long> getRepository() {
		return banVenduRepo;
	}

}
