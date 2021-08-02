package com.esmc.mcnp.dao.repository.obpsd;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuRelevebancairedetail;

@Repository
public interface EuRelevebancairedetailRepository extends BaseRepository<EuRelevebancairedetail, Integer> {
	@Query("select max(r.relevebancairedetailId) from EuRelevebancairedetail r")
	Integer findLastInsertedRelevebancairedetail();

	EuRelevebancairedetail findByRelevebancairedetailNumero(String numero);
	
	EuRelevebancairedetail findByRelevebancairedetailNumeroAndPublier(String numero, int publier);
}
