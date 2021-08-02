package com.esmc.mcnp.domain.entity.security;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "eu_roles_permissions")
@Data
public class EuRolePermission implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_roles_permissions")
    private Integer idRolesPermissions;
    @ManyToOne
    @JoinColumn(name = "id_roles")
    private EuRoles role;
    @ManyToOne
    @JoinColumn(name = "id_permissions")
    private EuPermission permission;
}
