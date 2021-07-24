package com.esmc.mcnp.services.obps;

import java.util.List;

import com.esmc.mcnp.model.obps.EuAnnulationCommande;
import com.esmc.mcnp.services.base.BaseService;

public interface EuAnnulationCommandeService extends BaseService<EuAnnulationCommande, Integer> {
	public List<EuAnnulationCommande> findByComande(Integer codeCommande);
}
