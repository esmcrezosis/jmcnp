package com.esmc.mcnp.repositories.obps;

import java.util.List;

import com.esmc.mcnp.model.obps.EuAnnulationCommande;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuAnnulationCommandeRepository extends BaseRepository<EuAnnulationCommande, Integer> {
	List<EuAnnulationCommande> findByCodeCommande(Integer codeCommande);
}
