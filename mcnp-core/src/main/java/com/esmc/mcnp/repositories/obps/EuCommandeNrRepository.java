package com.esmc.mcnp.repositories.obps;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obps.EuCommandeNr;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuCommandeNrRepository extends BaseRepository<EuCommandeNr, Long> {
	@Query("select c from EuCommandeNr c where c.codeCommande= :codeCmd and statut =1 ")
	public EuCommandeNr findCommandeNrByCodeCommande(@Param("codeCmd") String codeCommande);
}
