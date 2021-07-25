package com.esmc.mcnp.repositories.security;

import com.esmc.mcnp.model.security.EuRoles;
import com.esmc.mcnp.model.security.EuUserRolesPermission;
import com.esmc.mcnp.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EuUserRolesPermissionRepository extends BaseRepository<EuUserRolesPermission, Integer> {

    @Query("select p.euRole from EuUserRolesPermission p where p.utilisateur.idUtilisateur = :id")
    List<EuRoles> findByUser(@Param("id") Long id);
}
