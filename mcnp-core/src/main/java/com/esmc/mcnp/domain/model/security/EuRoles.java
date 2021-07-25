package com.esmc.mcnp.model.security;

import com.esmc.mcnp.commons.model.PageBean;
import com.esmc.mcnp.model.odd.EuOdd;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "eu_roles")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Accessors(chain = true)
public class EuRoles extends PageBean<EuRoles> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_roles")
    private Integer idRoles;
    @Column(name = "code_roles", unique = true)
    private String codeRoles;
    @Column(name = "libelle_roles")
    private String libelleRoles;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_roles_id")
    private EuRoles parent;
    @Column(name = "date_roles")
    private LocalDate dateRoles;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_groupe_roles")
    private EuGroupeRoles groupeRoles;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_odd")
    private EuOdd euOdd;
    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<EuRolePermission> rolePermissions = new ArrayList<>();
    @JsonIgnore
    @Transient
    private List<Long> menuIdList;
    @JsonIgnore
    @Transient
    private List<Long> orgIdList;

    public EuRoles(Integer idRoles) {
        this.idRoles = idRoles;
    }

    public EuRolePermission addRolePermission(EuRolePermission rolePermission) {
        getRolePermissions().add(rolePermission);
        rolePermission.setRole(this);

        return rolePermission;
    }

    public EuRolePermission removeRolePermission(EuRolePermission rolePermission) {
        getRolePermissions().remove(rolePermission);
        rolePermission.setRole(null);

        return rolePermission;
    }
}
