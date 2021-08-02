package com.esmc.mcnp.dao.repository.obps;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obps.EuCommandeNr;

public interface EuCommandeNrRepository extends BaseRepository<EuCommandeNr, Long> {
	@Query("select c from EuCommandeNr c where c.codeCommande= :codeCmd and statut =1 ")
	public EuCommandeNr findCommandeNrByCodeCommande(@Param("codeCmd") String codeCommande);
}
