package com.esmc.mcnp.services.cmfh;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.model.cmfh.EuMembreFondateur11000;
import com.esmc.mcnp.services.base.CrudService;

public interface EuMembreFondateur11000Service extends CrudService<EuMembreFondateur11000, Long> {
	EuMembreFondateur11000 findByMembre(String codeMembre);

	Page<EuMembreFondateur11000> findByNumBon(Long numbon, Pageable pageable);

	Page<EuMembreFondateur11000> findByMembre(String codeMembre, Pageable pageable);
	
	Page<EuMembreFondateur11000> findByNom(String nom, Pageable pageable);

	Page<EuMembreFondateur11000> findByNomAndPrenom(String nom, String prenom, Pageable pageable);
}
