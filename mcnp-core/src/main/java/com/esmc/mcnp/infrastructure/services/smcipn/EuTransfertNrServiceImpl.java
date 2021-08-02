package com.esmc.mcnp.infrastructure.services.smcipn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuTransfertNrRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuTransfertNr;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euTransfertNrService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuTransfertNrServiceImpl extends BaseServiceImpl<EuTransfertNr, Long> implements EuTransfertNrService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuTransfertNrRepository transfertNrRepo;

	@Override
	public Long getLastInsertedId() {
		return transfertNrRepo.getLastInsertedId();
	}

	@Override
	protected BaseRepository<EuTransfertNr, Long> getRepository() {
		return transfertNrRepo;
	}

	@Override
	public List<EuTransfertNr> findByBenef(String codeMembre) {
		return transfertNrRepo.findBycodeMembreBenef(codeMembre);
	}

	@Override
	public List<EuTransfertNr> findbyTransfert(String codeMembre) {
		return transfertNrRepo.findByCodeMembreTransfert(codeMembre);
	}

	@Override
	public List<EuTransfertNr> findByBenefAndAppelOffre(String codeMembre, String numAppelOffre) {
		return transfertNrRepo.findByCodeMembreBenefAndNumAppelOffre(codeMembre, numAppelOffre);
	}

	@Override
	public List<EuTransfertNr> findByTransfertAndAppelOffre(String codeMembre, String numAppelOffre) {
		return transfertNrRepo.findByCodeMembreTransfertAndNumAppelOffre(codeMembre, numAppelOffre);
	}

	@Override
	public Page<EuTransfertNr> findByBenef(String codeMembre, Pageable pageable) {
		return transfertNrRepo.findBycodeMembreBenef(codeMembre, pageable);
	}

	@Override
	public Page<EuTransfertNr> findbyTransfert(String codeMembre, Pageable pageable) {
		return transfertNrRepo.findByCodeMembreTransfert(codeMembre, pageable);
	}

	@Override
	public Page<EuTransfertNr> findByBenefAndAppelOffre(String codeMembre, String numAppelOffre, Pageable pageable) {
		return transfertNrRepo.findByCodeMembreBenefAndNumAppelOffre(codeMembre, numAppelOffre, pageable);
	}

	@Override
	public Page<EuTransfertNr> findByTransfertAndAppelOffre(String codeMembre, String numAppelOffre,
			Pageable pageable) {
		return transfertNrRepo.findByCodeMembreTransfertAndNumAppelOffre(codeMembre, numAppelOffre, pageable);
	}

}
