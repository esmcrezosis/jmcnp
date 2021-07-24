package com.esmc.mcnp.services.ot;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.model.ot.EuCandidature;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EuCandidatureService extends CrudService<EuCandidature, Integer> {
	List<EuCandidature> findByDateExpiration(LocalDate dateExp);

	Page<EuCandidature> findByDateExpiration(LocalDate dateExp, Pageable pageable);

	List<EuCandidature> findByDate(LocalDate date);

	Page<EuCandidature> findByDate(LocalDate date, Pageable pageable);

	List<EuCandidature> findByPosteId(Integer idPoste);

	List<EuCandidature> findByCodePoste(String codePoste);

	List<EuCandidature> findByLibellePoste(String libelle);

	Page<EuCandidature> findByPosteId(Integer idPoste, Pageable pageable);

	Page<EuCandidature> findByCodePoste(String codePoste, Pageable pageable);

	Page<EuCandidature> findByLibellePoste(String libelle, Pageable pageable);
}
