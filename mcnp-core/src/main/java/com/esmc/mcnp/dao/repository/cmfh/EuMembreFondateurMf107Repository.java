package com.esmc.mcnp.repositories.cmfh;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.model.cmfh.EuMembreFondateur107;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuMembreFondateurMf107Repository extends BaseRepository<EuMembreFondateur107, String> {

	EuMembreFondateur107 findByCodeMembre(String codeMembre);
	
	Page<EuMembreFondateur107> findByNumident(String numident, Pageable pageable);

	Page<EuMembreFondateur107> findByCodeMembre(String codeMembre, Pageable pageable);
	
	Page<EuMembreFondateur107> findByNom(String nom, Pageable pageable);

	Page<EuMembreFondateur107> findByNomAndPrenom(String nom, String prenom, Pageable pageable);
}
