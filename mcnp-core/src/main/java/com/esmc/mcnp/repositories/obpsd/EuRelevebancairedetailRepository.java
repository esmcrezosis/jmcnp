package com.esmc.mcnp.repositories.obpsd;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.model.obpsd.EuRelevebancairedetail;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Repository
public interface EuRelevebancairedetailRepository extends BaseRepository<EuRelevebancairedetail, Integer> {
	@Query("select max(r.relevebancairedetailId) from EuRelevebancairedetail r")
	Integer findLastInsertedRelevebancairedetail();

	EuRelevebancairedetail findByRelevebancairedetailNumero(String numero);
	
	EuRelevebancairedetail findByRelevebancairedetailNumeroAndPublier(String numero, int publier);
}
