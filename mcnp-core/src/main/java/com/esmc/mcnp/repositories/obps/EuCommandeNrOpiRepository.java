package com.esmc.mcnp.repositories.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obps.EuCommandeNrOpi;
import com.esmc.mcnp.model.obpsd.EuTraite;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuCommandeNrOpiRepository extends BaseRepository<EuCommandeNrOpi, Integer>{

	@Query("select c.euTraite from EuCommandeNrOpi c join c.euTraite t join c.euCommandeNr n  where n.codeCommande= :codeCommande")
	public List<EuTraite> findTraiteByCodeCommande(@Param("codeCommande") String codeCommande);

	
}
