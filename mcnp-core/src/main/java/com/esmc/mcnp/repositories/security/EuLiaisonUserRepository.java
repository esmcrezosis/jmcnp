package com.esmc.mcnp.repositories.security;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.org.EuDivisionGac;
import com.esmc.mcnp.model.acteur.EuLiaisonUser;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuLiaisonUserRepository extends BaseRepository<EuLiaisonUser, Integer> {
	@Query("select l.euDivisionGac from EuLiaisonUser l join fetch l.euDivisionGac.euGac g where l.utilisateur.idUtilisateur = :id")
	EuDivisionGac findByUtilisateur_id(@Param("id") Long idUser);
}
