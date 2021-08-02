package com.esmc.mcnp.infrastructure.services.op;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.domain.entity.op.EuOffreurProjet;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuOffreurProjetService extends BaseService<EuOffreurProjet, Integer> {

	Page<EuOffreurProjet> findAll(Pageable pageable);

	Page<EuOffreurProjet> findByProduit(String produit, Pageable pageable);

	Page<EuOffreurProjet> findByMembre(String codeMembre, Pageable pageable);

	Page<EuOffreurProjet> findByDescription(String description, Pageable pageable);

	Page<EuOffreurProjet> findByProduitOrDescription(String produit, String description, Pageable pageable);
}
