package com.esmc.mcnp.infrastructure.services.bc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuDetailBonOpiRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuDetailBonOpi;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
