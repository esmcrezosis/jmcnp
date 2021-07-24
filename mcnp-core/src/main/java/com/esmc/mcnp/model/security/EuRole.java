package com.esmc.mcnp.model.security;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the eu_role database table.
 * 
 */
@Entity
@Table(name = "eu_role")
@NamedQuery(name = "EuRole.findAll", query = "SELECT e FROM EuRole e")
public class EuRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idRole;
	private String nomRole;
	private List<EuUserRole> euUserRoles;

	public EuRole() {
	}

	public EuRole(String nomRole) {
		this.nomRole = nomRole;
	}

	@Id
	@Column(name = "id_role", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getIdRole() {
		return this.idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	@Column(name = "nom_role", nullable = false, length = 255)
	public String getNomRole() {
		return this.nomRole;
	}

	public void setNomRole(String nomRole) {
		this.nomRole = nomRole;
	}

	// bi-directional many-to-one association to EuUserRole
	@OneToMany(mappedBy = "euRole")
	public List<EuUserRole> getEuUserRoles() {
		return this.euUserRoles;
	}

	public void setEuUserRoles(List<EuUserRole> euUserRoles) {
		this.euUserRoles = euUserRoles;
	}

	public EuUserRole addEuUserRole(EuUserRole euUserRole) {
		getEuUserRoles().add(euUserRole);
		euUserRole.setEuRole(this);

		return euUserRole;
	}

	public EuUserRole removeEuUserRole(EuUserRole euUserRole) {
		getEuUserRoles().remove(euUserRole);
		euUserRole.setEuRole(null);

		return euUserRole;
	}

}