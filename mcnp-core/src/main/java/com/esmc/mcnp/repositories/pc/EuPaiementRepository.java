package com.esmc.mcnp.repositories.pc;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.pc.EuPaiement;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuPaiementRepository extends BaseRepository<EuPaiement, Integer> {

	List<EuPaiement> findByEuDemandePaiement_IdDemandePaiement(Integer idDemande);

	@Query("select p from EuPaiement p where p.euDemandePaiement.idDemandePaiement = :id and (p.payer = 0 or p.payer is null)")
	List<EuPaiement> findPaiementByIdDemandePaiement(@Param("id") Integer idDemande);

	@Query("select sum(p.montantPaiement) from EuPaiement p where p.euDemandePaiement.idDemandePaiement = :id")
	Optional<Double> getSumByDemandePaiement(@Param("id") Integer id);
}
