package com.esmc.mcnp.infrastructure.services.smcipn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuAppelOffreRepository;
import com.esmc.mcnp.domain.entity.op.EuAppelOffre;
import com.esmc.mcnp.domain.entity.others.EuProposition;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euAppelOffreService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuAppelOffreServiceImpl extends BaseServiceImpl<EuAppelOffre, Long> implements EuAppelOffreService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuAppelOffreRepository appelOffreRepo;

	@Override
	protected BaseRepository<EuAppelOffre, Long> getRepository() {
		return appelOffreRepo;
	}

	@Override
	public EuProposition findPropositionByAppelOffre(String numeroOffre) {
		return appelOffreRepo.findPropositionByAppelOffre(numeroOffre);
	}

	@Override
	public EuAppelOffre findAppelOffresByNumero(String numeroOffre) {
		return appelOffreRepo.findAppelOffresByNumero(numeroOffre);
	}

	@Override
	public EuProposition findPropositionByNumero(String numeroOffre) {
		return appelOffreRepo.findPropositionByNumero(numeroOffre);
	}

	@Override
	public Long getLastinsertedId() {
		return appelOffreRepo.getLastinsertedId();
	}

}
