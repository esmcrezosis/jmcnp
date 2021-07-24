package com.esmc.mcnp.web.model.security;

import java.io.Serializable;

public class UserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idUser;
	private String login;
	private String password;
	private String vPassword;
	private Integer dureeValidite;
	private Long idUtilisateur;
	private Integer idRole;

	public UserDto() {
		// TODO Auto-generated constructor stub
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getvPassword() {
		return vPassword;
	}

	public void setvPassword(String vPassword) {
		this.vPassword = vPassword;
	}

	public Integer getDureeValidite() {
		return dureeValidite;
	}

	public void setDureeValidite(Integer dureeValidite) {
		this.dureeValidite = dureeValidite;
	}

	public Long getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public Integer getIdRole() {
		return idRole;
	}

	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}

}
