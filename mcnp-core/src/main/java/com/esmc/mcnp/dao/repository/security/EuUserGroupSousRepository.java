package com.esmc.mcnp.dao.repository.security;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.security.EuUtilisateurGroupSous;
import com.esmc.mcnp.domain.entity.security.EuUtilisateurGroupSousPK;

public interface EuUserGroupSousRepository extends BaseRepository<EuUtilisateurGroupSous, EuUtilisateurGroupSousPK> {
	@Query("select g.id.codeGroupeSous from EuUtilisateurGroupSous g where g.id.idUtilisateur= :idUser")
	public String findSousGroupeByUserID(@Param("idUser") BigInteger idUser);
}
