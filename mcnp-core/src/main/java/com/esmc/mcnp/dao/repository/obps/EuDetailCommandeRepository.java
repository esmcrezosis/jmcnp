package com.esmc.mcnp.dao.repository.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obps.EuDetailCommande;

public interface EuDetailCommandeRepository extends BaseRepository<EuDetailCommande, Long> {

	@Query("select d from EuDetailCommande d join fetch d.euCommande c where c.codeCommande = :codeCmd")
	List<EuDetailCommande> getListDetailCommande(@Param("codeCmd") Long codeCommande);
}
