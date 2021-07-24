package com.esmc.mcnp.services.pc;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.pc.EuDetailPaiement;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.pc.EuDetailPaiementRepository;

@Service("euDetailPaiementService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuDetailPaiementServiceImpl extends BaseServiceImpl<EuDetailPaiement, Integer>
		implements EuDetailPaiementService {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuDetailPaiementRepository detPaieRepo;

	@Override
	protected BaseRepository<EuDetailPaiement, Integer> getRepository() {
		return detPaieRepo;
	}

	@Override
	public EuDetailPaiement findByTableAndId(String table, Integer id) {
		return detPaieRepo.findByTableAndIdTable(table, id);
	}

	@Override
	public List<EuDetailPaiement> findByNumeroDemande(String numero) {
		return detPaieRepo.findContratByNumeroDemandePaiement(numero);
	}

	@Override
	public List<EuDetailPaiement> findByIdDemandePaiement(Integer id) {
		return detPaieRepo.findDetailPaiementByIdDemandePaiement(id);
	}
}
