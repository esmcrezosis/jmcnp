package com.esmc.mcnp.domain.dto.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.esmc.mcnp.domain.entity.security.EuPermission;
import com.esmc.mcnp.domain.entity.security.EuRoles;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserPermission implements Serializable {

    private int idUserRolesPermissions;
    private String codeResponsabilite;
    private LocalDateTime dateAffectationRoles;
    private String libelleResponsabilite;
    private byte responsabilite;
    private User utilisateur;
    private Permission permission;
    private Roles roles;
}
