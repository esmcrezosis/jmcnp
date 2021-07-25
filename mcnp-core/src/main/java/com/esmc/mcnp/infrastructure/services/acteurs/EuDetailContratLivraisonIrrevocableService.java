package com.esmc.mcnp.services.acteurs;

import java.util.List;

import com.esmc.mcnp.model.acteur.EuDetailContratLivraisonIrrevocable;
import com.esmc.mcnp.services.base.BaseService;

/**
 * Created by USER on 13/03/2017.
 */
public interface EuDetailContratLivraisonIrrevocableService
		extends BaseService<EuDetailContratLivraisonIrrevocable, Long> {
	public List<EuDetailContratLivraisonIrrevocable> findByContrat(Integer idContrat);

	public Double getSumMontant(Integer idContrat);
}
