package com.esmc.mcnp.repositories.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obps.EuDetailCommande;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuDetailCommandeRepository extends BaseRepository<EuDetailCommande, Long> {

	@Query("select d from EuDetailCommande d join fetch d.euCommande c where c.codeCommande = :codeCmd")
	List<EuDetailCommande> getListDetailCommande(@Param("codeCmd") Long codeCommande);
}
