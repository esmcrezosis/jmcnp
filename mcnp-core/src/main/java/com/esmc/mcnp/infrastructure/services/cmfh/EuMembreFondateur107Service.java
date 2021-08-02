package com.esmc.mcnp.infrastructure.services.cmfh;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.domain.entity.cmfh.EuMembreFondateur107;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

public interface EuMembreFondateur107Service extends CrudService<EuMembreFondateur107, String> {
	EuMembreFondateur107 findByMembre(String codeMembre);

	Page<EuMembreFondateur107> findByNumident(String numident, Pageable pageable);

	Page<EuMembreFondateur107> findByMembre(String codeMembre, Pageable pageable);

	Page<EuMembreFondateur107> findByNom(String nom, Pageable pageable);

	Page<EuMembreFondateur107> findByNomAndPrenom(String nom, String prenom, Pageable pageable);
}
