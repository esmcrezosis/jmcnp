package com.esmc.mcnp.services.ba;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuBonNeutreTiers;
import com.esmc.mcnp.repositories.obpsd.EuBonNeutreTiersRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

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
