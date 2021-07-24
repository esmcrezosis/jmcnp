package com.esmc.mcnp.repositories.op;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.op.EuOffreurProjet;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuOffreurProjetRepository extends BaseRepository<EuOffreurProjet, Integer> {

	Page<EuOffreurProjet> findByCodeMembreContaining(String codeMembre, Pageable pageable);
	
	Page<EuOffreurProjet> findByProduitContaining(String produit, Pageable pageable);

	Page<EuOffreurProjet> findByDescriptionProjetContaining(String descriptionProjet, Pageable pageable);
	
	@Query("select o from EuOffreurProjet o where o.produit like %:produit% or o.descriptionProjet like %:description%")
	Page<EuOffreurProjet> findByProduitOrDescription(String produit, String description, Pageable pageable);
}
