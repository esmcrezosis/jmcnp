package com.esmc.mcnp.dao.repository.security;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.acteur.EuLiaisonUser;
import com.esmc.mcnp.domain.entity.org.EuDivisionGac;

public interface EuLiaisonUserRepository extends BaseRepository<EuLiaisonUser, Integer> {
	@Query("select l.euDivisionGac from EuLiaisonUser l join fetch l.euDivisionGac.euGac g where l.utilisateur.idUtilisateur = :id")
	EuDivisionGac findByUtilisateur_id(@Param("id") Long idUser);
}
