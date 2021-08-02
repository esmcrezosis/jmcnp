package com.esmc.mcnp.dao.repository.security;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.security.EuRoles;
import com.esmc.mcnp.domain.entity.security.EuUserRolesPermission;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EuUserRolesPermissionRepository extends BaseRepository<EuUserRolesPermission, Integer> {

    @Query("select p.euRole from EuUserRolesPermission p where p.utilisateur.idUtilisateur = :id")
    List<EuRoles> findByUser(@Param("id") Long id);
}
