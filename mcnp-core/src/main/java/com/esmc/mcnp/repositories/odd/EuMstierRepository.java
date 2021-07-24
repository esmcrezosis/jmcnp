package com.esmc.mcnp.repositories.odd;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.odd.EuMstier;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuMstierRepository extends BaseRepository<EuMstier, Integer> {

	List<EuMstier> findByCodeMembre(String codeMembre);

	@Query("select sum(m.montantRestant) From EuMstier m where m.codeMembre like :membre")
	Double findSumByCodeMembre(@Param("membre") String codeMembre);

	List<EuMstier> findByCodeMembreAndStatutMstiers(String codeMembre, String statutMstiers);

	@Query("select sum(m.montantRestant) From EuMstier m where m.codeMembre like :membre and m.statutMstiers like :statut")
	Double findSumByCodeMembreAndStatut(@Param("membre") String codeMembre, @Param("statut") String statut);

	List<EuMstier> findByStatutMstiers(String statut);

	@Query("select sum(m.montantRestant) From EuMstier m where m.statutMstiers like :statut")
	Double findSumByStatut(@Param("statut") String statut);

	List<EuMstier> findByCodeMembreAndTypeMstiersAndStatutMstiers(String codeMembre, String type, String statut);

	List<EuMstier> findByTypeMstiersAndStatutMstiers(String type, String statut);

	@Query("select sum(m.montantRestant) From EuMstier m where m.codeMembre like :membre and m.typeMstiers like :type and m.statutMstiers like :statut")
	Double sumByCodeMembreAndTypeAndStatut(@Param("membre") String codeMembre, @Param("type") String type,
			@Param("statut") String statut);

	@Query("select sum(m.montantRestant) From EuMstier m where m.typeMstiers like :type and m.statutMstiers like :statut")
	Double sumByTypeAndStatut(@Param("type") String type, @Param("statut") String statut);
}
