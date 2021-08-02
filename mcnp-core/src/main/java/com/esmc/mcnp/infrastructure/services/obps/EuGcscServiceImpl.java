package com.esmc.mcnp.infrastructure.services.obps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuGcscRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuGcsc;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euGcscService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuGcscServiceImpl extends BaseServiceImpl<EuGcsc, Long> implements EuGcscService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuGcscRepository gcscRepo;
	@Override
	public Long findLastGcscInsertedId() {
		return gcscRepo.findLastGcscInsertedId();
	}

	@Override
	public EuGcsc findByEuSmcipn_CodeSmcipn(String codeSmcipn) {
		return gcscRepo.findByEuSmcipn_CodeSmcipn(codeSmcipn);
	}

	@Override
	public EuGcsc findBySmcipnAndBenef(String codeSmcipn, String codeMembre) {
		return gcscRepo.findBySmcipnAndBenef(codeSmcipn, codeMembre);
	}

	@Override
	public EuGcsc findByEuMembreMorale_CodeMembreMorale(String codemembre) {
		return gcscRepo.findByEuMembreMorale_CodeMembreMorale(codemembre);
	}

	@Override
	public EuGcsc findByNumeroAppelOffre(String numeroAppelOffre) {
		return gcscRepo.findByNumeroAppelOffre(numeroAppelOffre);
	}

	@Override
	protected BaseRepository<EuGcsc, Long> getRepository() {
		return gcscRepo;
	}

}
