package com.esmc.mcnp.services.pc;

import java.util.List;

import com.esmc.mcnp.model.pc.EuDemandePaiement;
import com.esmc.mcnp.services.base.BaseService;

public interface EuDemandePaiementService extends BaseService<EuDemandePaiement, Integer> {
	public EuDemandePaiement findByNumeroDemandePaiement(String numero);

	public List<EuDemandePaiement> findByCodeMembreEmployeurAndTypeDemande(String codeMembre, String typeDemande);

	public List<EuDemandePaiement> findByCodeMembrerAndType(String codeMembre, String typeDemande);
	
	public List<EuDemandePaiement> findByMembreAndPayer(String codeMembre, Integer payer);
	
	public List<EuDemandePaiement> findDemandeNonPayer();
	
	public List<EuDemandePaiement> findDemandeNonPayer(int deb, int nbre);
}
