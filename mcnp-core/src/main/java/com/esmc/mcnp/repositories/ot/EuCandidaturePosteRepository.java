package com.esmc.mcnp.repositories.ot;

import com.esmc.mcnp.model.ot.EuCandidaturePoste;
import com.esmc.mcnp.repositories.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface EuCandidaturePosteRepository extends BaseRepository<EuCandidaturePoste, Integer> {

	@EntityGraph(attributePaths = { "poste" })
	EuCandidaturePoste findByidCandidaturePoste(Integer id);

	List<EuCandidaturePoste> findByPoste_CodeRoles(String codePoste);

	Page<EuCandidaturePoste> findByPoste_CodeRoles(String codePoste, Pageable page);

	List<EuCandidaturePoste> findByPoste_LibelleRolesLike(String libelle);

	Page<EuCandidaturePoste> findByPoste_LibelleRolesLike(String codePoste, Pageable page);

	List<EuCandidaturePoste> findByCandidature_IdCandidature(Integer idCandidature);

	Page<EuCandidaturePoste> findByCandidature_IdCandidature(Integer idCandidature, Pageable pageable);
}
