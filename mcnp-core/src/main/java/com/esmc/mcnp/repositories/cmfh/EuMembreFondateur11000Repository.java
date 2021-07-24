package com.esmc.mcnp.repositories.cmfh;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.model.cmfh.EuMembreFondateur11000;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuMembreFondateur11000Repository extends BaseRepository<EuMembreFondateur11000, Long> {
	EuMembreFondateur11000 findByCodeMembre(String codeMembre);

	Page<EuMembreFondateur11000> findByNumBon(Long numbon, Pageable pageable);

	Page<EuMembreFondateur11000> findByCodeMembre(String codeMembre, Pageable pageable);
	
	Page<EuMembreFondateur11000> findByNom(String nom, Pageable pageable);

	Page<EuMembreFondateur11000> findByNomAndPrenom(String nom, String prenom, Pageable pageable);
}
