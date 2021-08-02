package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_cardsemboss database table.
 * 
 */
@Entity
@Table(name="eu_cardsemboss")
@NamedQuery(name="EuCardsemboss.findAll", query="SELECT e FROM EuCardsemboss e")
public class EuCardsemboss implements Serializable {
	private static final long serialVersionUID = 1L;
	private double neng;
	private String codeCat;
	private String codeCompte;
	private String codeMembre;
	private String nomMembre;
	private String prenomMembre;

	public EuCardsemboss() {
	}


	@Id
	@Column(unique=true, nullable=false)
	public double getNeng() {
		return this.neng;
	}

	public void setNeng(double neng) {
		this.neng = neng;
	}


	@Column(name="code_cat", length=60)
	public String getCodeCat() {
		return this.codeCat;
	}

	public void setCodeCat(String codeCat) {
		this.codeCat = codeCat;
	}


	@Column(name="code_compte", length=100)
	public String getCodeCompte() {
		return this.codeCompte;
	}

	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}


	@Column(name="code_membre", length=255)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(name="nom_membre", length=255)
	public String getNomMembre() {
		return this.nomMembre;
	}

	public void setNomMembre(String nomMembre) {
		this.nomMembre = nomMembre;
	}


	@Column(name="prenom_membre", length=255)
	public String getPrenomMembre() {
		return this.prenomMembre;
	}

	public void setPrenomMembre(String prenomMembre) {
		this.prenomMembre = prenomMembre;
	}

}