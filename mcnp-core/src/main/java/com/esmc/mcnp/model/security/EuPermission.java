package com.esmc.mcnp.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the eu_permissions database table.
 */
@Data
@Entity
@Table(name = "eu_permissions")
@NamedQuery(name = "EuPermission.findAll", query = "SELECT e FROM EuPermission e")
public class EuPermission implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permissions")
    private int idPermissions;
    @Column(name = "libelle_permissions")
    private String libellePermissions;
    //bi-directional many-to-one association to EuUserRolesPermission
    @JsonIgnore
    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY)
    private List<EuRolePermission> rolePermissions;

    public EuRolePermission addRolePermission(EuRolePermission rolePermission) {
        getRolePermissions().add(rolePermission);
        rolePermission.setPermission(this);

        return rolePermission;
    }

    public EuRolePermission removeRolePermission(EuRolePermission rolePermission) {
        getRolePermissions().remove(rolePermission);
        rolePermission.setPermission(null);

        return rolePermission;
    }

}