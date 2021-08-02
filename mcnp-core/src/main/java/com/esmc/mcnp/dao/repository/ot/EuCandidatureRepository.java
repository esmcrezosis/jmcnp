package com.esmc.mcnp.dao.repository.ot;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.ot.EuCandidature;

public interface EuCandidatureRepository extends BaseRepository<EuCandidature, Integer> {

	@Query(value = "select c from EuCandidature c where c.dateExpiration < :date")
	List<EuCandidature> findByDateExpiration(@Param("date") LocalDate dateExp);
	
	@Query(value = "select c from EuCandidature c where c.dateExpiration < :date")
	Page<EuCandidature> findByDateExpiration(@Param("date") LocalDate dateExp, Pageable pageable);
	
	@Query(value = "select c from EuCandidature c where c.dateCandidature = :date")
	List<EuCandidature> findByDate(@Param("date") LocalDate date);
	
	@Query(value = "select c from EuCandidature c where c.dateCandidature = :date")
	Page<EuCandidature> findByDate(@Param("date") LocalDate date, Pageable pageable);

	@Query(value = "select c from EuCandidature c left join c.postes p where p.poste.idRoles = :idposte")
	List<EuCandidature> findByPosteId(@Param("idposte") Integer idPoste);

	@Query(value = "select c from EuCandidature c left join c.postes p where p.poste.codeRoles = :codeposte")
	List<EuCandidature> findByCodePoste(@Param("codeposte") String codePoste);

	@Query(value = "select c from EuCandidature c left join c.postes p where p.poste.libelleRoles like :poste")
	List<EuCandidature> findByLibellePoste(@Param("poste") String libelle);

	@Query(value = "select c from EuCandidature c left join c.postes p where p.poste.idRoles = :idposte")
	Page<EuCandidature> findByPosteId(@Param("idposte") Integer idPoste, Pageable pageable);

	@Query(value = "select c from EuCandidature c left join c.postes p where p.poste.codeRoles = :codeposte")
	Page<EuCandidature> findByCodePoste(@Param("codeposte") String codePoste, Pageable pageable);

	@Query(value = "select c from EuCandidature c left join c.postes p where p.poste.libelleRoles like :poste")
	Page<EuCandidature> findByLibellePoste(@Param("poste") String libelle, Pageable pageable);
}
