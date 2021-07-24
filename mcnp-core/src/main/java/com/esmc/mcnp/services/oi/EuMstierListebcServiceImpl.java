package com.esmc.mcnp.services.oi;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.odd.EuMstierListebc;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.odd.EuMstierListebcRepository;

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
