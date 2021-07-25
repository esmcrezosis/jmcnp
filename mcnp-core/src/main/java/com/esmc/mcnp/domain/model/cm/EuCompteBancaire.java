package com.esmc.mcnp.model.cm;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the eu_compte_bancaire database table.
 * 
 */
@Entity
@Table(name = "eu_compte_bancaire")
@NamedQuery(name = "EuCompteBancaire.findAll", query = "SELECT e FROM EuCompteBancaire e")
public class EuCompteBancaire implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idCompte;
	private String codeBanque;
	private String codeMembre;
	private String codeMembreMorale;
	private String numCompteBancaire;
	private int principal;

	public EuCompteBancaire() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_compte", unique = true, nullable = false)
	public Integer getIdCompte() {
		return this.idCompte;
	}

	public void setIdCompte(Integer idCompte) {
		this.idCompte = idCompte;
	}

	@Column(name = "code_banque", length = 40)
	public String getCodeBanque() {
		return this.codeBanque;
	}

	public void setCodeBanque(String codeBanque) {
		this.codeBanque = codeBanque;
	}

	@Column(name = "code_membre", length = 100)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name = "code_membre_morale", length = 100)
	public String getCodeMembreMorale() {
		return this.codeMembreMorale;
	}

	public void setCodeMembreMorale(String codeMembreMorale) {
		this.codeMembreMorale = codeMembreMorale;
	}

	@Column(name = "num_compte_bancaire", length = 180)
	public String getNumCompteBancaire() {
		return this.numCompteBancaire;
	}

	public void setNumCompteBancaire(String numCompteBancaire) {
		this.numCompteBancaire = numCompteBancaire;
	}

	@Column(name = "principal")
	public int getPrincipal() {
		return principal;
	}

	public void setPrincipal(int principal) {
		this.principal = principal;
	}
}