package com.esmc.mcnp.infrastructure.services.ba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuBonNeutreTiersRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreTiers;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euBonNeutreTiersService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuBonNeutreTiersServiceImpl extends BaseServiceImpl<EuBonNeutreTiers, Integer>
		implements EuBonNeutreTiersService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuBonNeutreTiersRepository bonTiersRepo;

	@Override
	protected BaseRepository<EuBonNeutreTiers, Integer> getRepository() {
		return bonTiersRepo;
	}

}
