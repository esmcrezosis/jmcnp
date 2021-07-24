package com.esmc.mcnp.services.smcipn;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.op.EuAppelOffre;
import com.esmc.mcnp.model.others.EuProposition;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.smcipn.EuAppelOffreRepository;

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
