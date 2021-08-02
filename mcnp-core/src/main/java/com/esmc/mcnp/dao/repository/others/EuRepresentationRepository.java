package com.esmc.mcnp.dao.repository.others;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cm.EuRepresentation;
import com.esmc.mcnp.domain.entity.cm.EuRepresentationPK;

public interface EuRepresentationRepository extends BaseRepository<EuRepresentation, EuRepresentationPK> {

	public List<EuRepresentation> findByEuMembreMorale_CodeMembreMorale(String code_membre_moral);

	@Query("select r from EuRepresentation r where codeMembreMorale like :codeMembreMorale and etat like 'inside'")
	public EuRepresentation findCodeMembreRepresentantPrincipal(@Param("codeMembreMorale") String codeMembreMorale);

	@EntityGraph(attributePaths = { "euMembreMorale", "euMembre" })
	EuRepresentation findByEuMembreMorale_CodeMembreMoraleAndEtat(String codeMembre, String etat);

	@EntityGraph(attributePaths = { "euMembreMorale", "euMembre" })
	EuRepresentation findByEuMembreMorale_CodeMembreMoraleAndEuMembre_CodeMembre(String codeMembreMorale,
			String codeMembre);
}
