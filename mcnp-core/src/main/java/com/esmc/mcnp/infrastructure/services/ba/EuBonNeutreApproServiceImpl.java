package com.esmc.mcnp.services.ba;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuBonNeutreAppro;
import com.esmc.mcnp.repositories.obpsd.EuBonNeutreApproRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service("euBonNeutreApproService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuBonNeutreApproServiceImpl extends BaseServiceImpl<EuBonNeutreAppro, Integer>
		implements EuBonNeutreApproService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuBonNeutreApproRepository bonApproRepo;

	@Override
	protected BaseRepository<EuBonNeutreAppro, Integer> getRepository() {
		return bonApproRepo;
	}

	@Override
	public int getLastInsertedId() {
		return bonApproRepo.getLastInsertedId();
	}

}
