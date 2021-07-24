package com.esmc.mcnp.model.acteur;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "eu_liaison_compte")
public class EuLiasonCompte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String codeCompteAdmin;
	private String codeCompteAnim;
	private LocalDateTime dateLiaison;

	public EuLiasonCompte() {
	}

	public EuLiasonCompte(String codeCompteAdmin, String codeCompteAnim) {
		this.codeCompteAdmin = codeCompteAdmin;
		this.codeCompteAnim = codeCompteAnim;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "code_membre_admin", unique = true)
	public String getCodeCompteAdmin() {
		return codeCompteAdmin;
	}

	public void setCodeCompteAdmin(String codeCompteAdmin) {
		this.codeCompteAdmin = codeCompteAdmin;
	}

	@Column(name = "code_membre_anim", unique = true)
	public String getCodeCompteAnim() {
		return codeCompteAnim;
	}

	public void setCodeCompteAnim(String codeCompteAnim) {
		this.codeCompteAnim = codeCompteAnim;
	}

	@Column(name = "date_liaison")
	public LocalDateTime getDateLiaison() {
		return dateLiaison;
	}

	public void setDateLiaison(LocalDateTime dateLiaison) {
		this.dateLiaison = dateLiaison;
	}

}
