package com.esmc.mcnp.model.security;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * The persistent class for the eu_user_roles_permissions database table.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "eu_user_roles_permissions")
@NamedQuery(name = "EuUserRolesPermission.findAll", query = "SELECT e FROM EuUserRolesPermission e")
public class EuUserRolesPermission implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_roles_permissions")
    private int idUserRolesPermissions;
    @Column(name = "code_responsabilite")
    private String codeResponsabilite;
    @Column(name = "date_affectation_roles")
    private LocalDateTime dateAffectationRoles;
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private EuUtilisateur utilisateur;
    @Column(name = "libelle_responsabilite")
    private String libelleResponsabilite;
    private Integer responsabilite;
    //bi-directional many-to-one association to EuRole
    @ManyToOne
    @JoinColumn(name = "id_roles")
    private EuRoles euRole;

}