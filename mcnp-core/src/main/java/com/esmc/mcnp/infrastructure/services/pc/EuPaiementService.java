package com.esmc.mcnp.infrastructure.services.pc;

import java.util.List;

import com.esmc.mcnp.domain.entity.pc.EuPaiement;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuPaiementService extends BaseService<EuPaiement, Integer> {

	List<EuPaiement> findByDemandePaiement(Integer id);

	List<EuPaiement> findPaiementByDemande(Integer demande);

	Double getSommeByDemande(Integer iddemande);
}
