package com.esmc.mcnp.dao.repository.obpsd;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuRelevebancaire;

@Repository
public interface EuRelevebancaireRepository extends BaseRepository<EuRelevebancaire, Integer> {

	@Query("select max(r.relevebancaireId) from EuRelevebancaire r")
	Integer findLastInsertedRelevebancaire();

	EuRelevebancaire findByRelevebancaireDateAndRelevebancaireBanque(Date date, String codeBanque);
}
