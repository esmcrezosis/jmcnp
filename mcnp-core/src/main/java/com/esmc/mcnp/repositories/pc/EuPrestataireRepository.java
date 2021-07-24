package com.esmc.mcnp.repositories.pc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.pc.EuPrestataire;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuPrestataireRepository extends BaseRepository<EuPrestataire, Long> {
	List<EuPrestataire> findByCodeMembrePrestataire(String codeMembrePrestataire);

	Page<EuPrestataire> findByCodeMembreContaining(String codeMembre, Pageable page);

	Page<EuPrestataire> findByCodeMembrePrestataireContaining(String codeMembrePrestataire, Pageable page);

	Page<EuPrestataire> findByNomMembrePrestataireContaining(String nomMembrePrestataire, Pageable page);

	@Query("select p from EuPrestataire p where p.codeMembre like %:membre% and p.codeMembrePrestataire like %:prestataire%")
	Page<EuPrestataire> findByMembreAndPrestataire(@Param("membre") String codeMembre,
			@Param("prestataire") String codeMembrePrestataire, Pageable page);
}
