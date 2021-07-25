package com.esmc.mcnp.repositories.security;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.security.EuUtilisateurGroupSous;
import com.esmc.mcnp.model.security.EuUtilisateurGroupSousPK;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuUserGroupSousRepository extends BaseRepository<EuUtilisateurGroupSous, EuUtilisateurGroupSousPK> {
	@Query("select g.id.codeGroupeSous from EuUtilisateurGroupSous g where g.id.idUtilisateur= :idUser")
	public String findSousGroupeByUserID(@Param("idUser") BigInteger idUser);
}
