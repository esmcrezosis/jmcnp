package com.esmc.mcnp.infrastructure.services.oi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.odd.EuMstierListebcRepository;
import com.esmc.mcnp.domain.entity.odd.EuMstierListebc;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuMstierListebcServiceImpl extends BaseServiceImpl<EuMstierListebc, Integer>
		implements EuMstierListebcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuMstierListebcRepository mstierlisteRepo;

	public EuMstierListebcServiceImpl() {
	}

	@Override
	public List<EuMstierListebc> findByStatut() {
		return mstierlisteRepo.findListeByStatut();
	}

	@Override
	public List<EuMstierListebc> findByStatut(Integer statut) {
		return mstierlisteRepo.findByStatut(statut);
	}

	@Override
	protected BaseRepository<EuMstierListebc, Integer> getRepository() {
		return mstierlisteRepo;
	}

	@Override
	public List<EuMstierListebc> findByBenef(String codeMembre) {
		return mstierlisteRepo.findByCodeMembreBenef(codeMembre);
	}

	@Override
	public List<EuMstierListebc> findByBenefAndOdd(String codeMembre, Integer idOdd) {
		return mstierlisteRepo.findByCodeMembreBenefAndIdOdd(codeMembre, idOdd);
	}

	@Override
	public EuMstierListebc findByCodeFicheOdd(String codeFicheOdd) {
		return mstierlisteRepo.findByCodeFicheOdd(codeFicheOdd);
	}

}
