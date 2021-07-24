package com.esmc.mcnp.services.pc;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.pc.EuPaiement;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.pc.EuPaiementRepository;

@Service("euPaiementService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuPaiementServiceImpl extends BaseServiceImpl<EuPaiement, Integer> implements EuPaiementService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuPaiementRepository paiementRepo;

	@Override
	protected BaseRepository<EuPaiement, Integer> getRepository() {
		return paiementRepo;
	}

	@Override
	public List<EuPaiement> findByDemandePaiement(Integer id) {
		return paiementRepo.findByEuDemandePaiement_IdDemandePaiement(id);
	}

	@Override
	public Double getSommeByDemande(Integer iddemande) {
		return paiementRepo.getSumByDemandePaiement(iddemande).orElse(0.0);
	}

	@Override
	public List<EuPaiement> findPaiementByDemande(Integer demande) {
		return paiementRepo.findPaiementByIdDemandePaiement(demande);
	}

}
