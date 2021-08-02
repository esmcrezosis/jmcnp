package com.esmc.mcnp.dao.repository.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obps.EuCommandeNrOpi;
import com.esmc.mcnp.domain.entity.obpsd.EuTraite;

public interface EuCommandeNrOpiRepository extends BaseRepository<EuCommandeNrOpi, Integer>{

	@Query("select c.euTraite from EuCommandeNrOpi c join c.euTraite t join c.euCommandeNr n  where n.codeCommande= :codeCommande")
	public List<EuTraite> findTraiteByCodeCommande(@Param("codeCommande") String codeCommande);

	
}
