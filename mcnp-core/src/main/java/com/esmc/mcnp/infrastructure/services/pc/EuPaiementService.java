package com.esmc.mcnp.services.pc;

import java.util.List;

import com.esmc.mcnp.model.pc.EuPaiement;
import com.esmc.mcnp.services.base.BaseService;

public interface EuPaiementService extends BaseService<EuPaiement, Integer> {

	List<EuPaiement> findByDemandePaiement(Integer id);

	List<EuPaiement> findPaiementByDemande(Integer demande);

	Double getSommeByDemande(Integer iddemande);
}
