package com.esmc.mcnp.dao.repository.obps;

import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obps.EuAnnulationCommande;

public interface EuAnnulationCommandeRepository extends BaseRepository<EuAnnulationCommande, Integer> {
	List<EuAnnulationCommande> findByCodeCommande(Integer codeCommande);
}
