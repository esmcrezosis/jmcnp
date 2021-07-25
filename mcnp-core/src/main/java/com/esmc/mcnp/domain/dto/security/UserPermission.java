package com.esmc.mcnp.dto.security;

import com.esmc.mcnp.model.security.EuPermission;
import com.esmc.mcnp.model.security.EuRoles;
import com.esmc.mcnp.model.security.EuUtilisateur;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
