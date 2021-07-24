package com.esmc.mcnp.repositories.acteurs;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.acteur.EuDetailContratLivraisonIrrevocable;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 * Created by USER on 13/03/2017.
 */
public interface EuDetailContratLivraisonIrrevocableRepository
		extends BaseRepository<EuDetailContratLivraisonIrrevocable, Long> {
	List<EuDetailContratLivraisonIrrevocable> findByContrat_IdContrat(Integer idcontrat);

	@Query("select sum(d.montantProduit) from EuDetailContratLivraisonIrrevocable d where d.contrat.idContrat = :id")
	Optional<Double> getSumByIdContrat(@Param("id") Integer idContrat);
}
