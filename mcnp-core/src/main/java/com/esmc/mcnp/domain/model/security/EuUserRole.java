package com.esmc.mcnp.model.security;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_user_role database table.
 * 
 */
@Entity
@Table(name="eu_user_role")
@NamedQuery(name="EuUserRole.findAll", query="SELECT e FROM EuUserRole e")
public class EuUserRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idUserRole;
	private EuRole euRole;
	private EuUser euUser;

	public EuUserRole() {
	}


	public EuUserRole(EuRole euRole, EuUser euUser) {
		this.euRole = euRole;
		this.euUser = euUser;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_user_role", unique=true, nullable=false)
	public int getIdUserRole() {
		return this.idUserRole;
	}

	public void setIdUserRole(int idUserRole) {
		this.idUserRole = idUserRole;
	}


	//bi-directional many-to-one association to EuRole
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_role", nullable=false)
	public EuRole getEuRole() {
		return this.euRole;
	}

	public void setEuRole(EuRole euRole) {
		this.euRole = euRole;
	}


	//bi-directional many-to-one association to EuUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_user", nullable=false)
	public EuUser getEuUser() {
		return this.euUser;
	}

	public void setEuUser(EuUser euUser) {
		this.euUser = euUser;
	}

}