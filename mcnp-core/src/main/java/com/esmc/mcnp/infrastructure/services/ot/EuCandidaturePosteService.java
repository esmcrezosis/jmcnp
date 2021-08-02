package com.esmc.mcnp.infrastructure.services.ot;

import com.esmc.mcnp.domain.entity.ot.EuCandidaturePoste;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuCandidaturePosteService extends CrudService<EuCandidaturePoste, Integer> {
	EuCandidaturePoste findById(Integer id);

	List<EuCandidaturePoste> findByCodePoste(String codePoste);

	Page<EuCandidaturePoste> findByCodePoste(String codePoste, Pageable page);

	List<EuCandidaturePoste> findByLibellePoste(String libelle);

	Page<EuCandidaturePoste> findByLibellePoste(String codePoste, Pageable page);

	List<EuCandidaturePoste> findByCandidature(Integer idCandidature);

	Page<EuCandidaturePoste> findByCandidature(Integer idCandidature, Pageable pageable);
}
