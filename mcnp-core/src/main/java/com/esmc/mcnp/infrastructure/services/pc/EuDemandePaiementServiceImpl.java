package com.esmc.mcnp.infrastructure.services.pc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.pc.EuDemandePaiementRepository;
import com.esmc.mcnp.domain.entity.pc.EuDemandePaiement;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euDemandePaiementService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuDemandePaiementServiceImpl extends BaseServiceImpl<EuDemandePaiement, Integer>
		implements EuDemandePaiementService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuDemandePaiementRepository demPaieRepo;

	@Override
	protected BaseRepository<EuDemandePaiement, Integer> getRepository() {
		return demPaieRepo;
	}

	@Override
	public EuDemandePaiement findByNumeroDemandePaiement(String numero) {
		return demPaieRepo.findByNumeroDemandePaiement(numero);
	}

	@Override
	public List<EuDemandePaiement> findByCodeMembreEmployeurAndTypeDemande(String codeMembre, String typeDemande) {
		return demPaieRepo.findByCodeMembreEmployeurAndTypeDemande(codeMembre, typeDemande);
	}

	@Override
	public List<EuDemandePaiement> findByCodeMembrerAndType(String codeMembre, String typeDemande) {
		return demPaieRepo.findByCodeMembreAndType(codeMembre, typeDemande);
	}

	@Override
	public List<EuDemandePaiement> findByMembreAndPayer(String codeMembre, Integer payer) {
		return demPaieRepo.findByCodeMembreEmployeurAndPayer(codeMembre, payer);
	}

	@Override
	public List<EuDemandePaiement> findDemandeNonPayer() {
		// TODO Auto-generated method stub
		return demPaieRepo.findDemandeNonPayer();
	}

	@Override
	public List<EuDemandePaiement> findDemandeNonPayer(int deb, int nbre) {
		return demPaieRepo.findDemandeNonPayer(PageRequest.of(deb, nbre));
	}

}
