package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuBanVendu;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.pc.EuBanVenduRepository;

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
