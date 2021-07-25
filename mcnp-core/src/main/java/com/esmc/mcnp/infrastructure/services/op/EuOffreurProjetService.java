package com.esmc.mcnp.services.op;

import com.esmc.mcnp.services.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.model.op.EuOffreurProjet;

public interface EuOffreurProjetService extends BaseService<EuOffreurProjet, Integer> {

	Page<EuOffreurProjet> findAll(Pageable pageable);

	Page<EuOffreurProjet> findByProduit(String produit, Pageable pageable);

	Page<EuOffreurProjet> findByMembre(String codeMembre, Pageable pageable);

	Page<EuOffreurProjet> findByDescription(String description, Pageable pageable);

	Page<EuOffreurProjet> findByProduitOrDescription(String produit, String description, Pageable pageable);
}
