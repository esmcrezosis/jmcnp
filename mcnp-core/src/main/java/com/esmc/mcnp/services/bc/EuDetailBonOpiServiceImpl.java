package com.esmc.mcnp.services.bc;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuDetailBonOpi;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obps.EuDetailBonOpiRepository;

@Service("euDetailBonOpiService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuDetailBonOpiServiceImpl extends BaseServiceImpl<EuDetailBonOpi, Integer>
		implements EuDetailBonOpiService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuDetailBonOpiRepository detBonOpiRepo;

	@Override
	public Integer getLastInsertedId() {
		return detBonOpiRepo.getLastInsertedId();
	}

	@Override
	public EuDetailBonOpi findByEuBon_BonNumero(String numeroBon) {
		return detBonOpiRepo.findByEuBon_BonNumero(numeroBon);
	}

	@Override
	protected BaseRepository<EuDetailBonOpi, Integer> getRepository() {
		return detBonOpiRepo;
	}

	@Override
	public EuDetailBonOpi findByTpagcp(Long idTpagcp) {
		return detBonOpiRepo.findByIdTpagcp(idTpagcp);
	}

}
