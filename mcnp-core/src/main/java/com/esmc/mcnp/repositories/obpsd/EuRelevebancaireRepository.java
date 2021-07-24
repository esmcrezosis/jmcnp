package com.esmc.mcnp.repositories.obpsd;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.model.obpsd.EuRelevebancaire;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Repository
public interface EuRelevebancaireRepository extends BaseRepository<EuRelevebancaire, Integer> {

	@Query("select max(r.relevebancaireId) from EuRelevebancaire r")
	Integer findLastInsertedRelevebancaire();

	EuRelevebancaire findByRelevebancaireDateAndRelevebancaireBanque(Date date, String codeBanque);
}
