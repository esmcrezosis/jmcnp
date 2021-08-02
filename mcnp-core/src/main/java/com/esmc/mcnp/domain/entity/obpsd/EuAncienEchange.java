package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the eu_ancien_echange database table.
 * 
 */
@Entity
@Table(name="eu_ancien_echange")
@NamedQuery(name="EuAncienEchange.findAll", query="SELECT e FROM EuAncienEchange e")
public class EuAncienEchange implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idEchange;
	private double agio;
	private String catEchange;
	private String codeCompteEch;
	private String codeCompteObt;
	private String codeMembre;
	private String codeProduit;
	private byte compenser;
	private Date dateEchange;
	private Date dateReglement;
	private BigInteger idCredit;
	private BigInteger idUtilisateur;
	private double montant;
	private double montantEchange;
	private byte regler;
	private String typeEchange;

	public EuAncienEchange() {
	}


	@Id
	@Column(name="id_echange")
	public Long getIdEchange() {
		return this.idEchange;
	}

	public void setIdEchange(Long idEchange) {
		this.idEchange = idEchange;
	}


	public double getAgio() {
		return this.agio;
	}

	public void setAgio(double agio) {
		this.agio = agio;
	}


	@Column(name="cat_echange")
	public String getCatEchange() {
		return this.catEchange;
	}

	public void setCatEchange(String catEchange) {
		this.catEchange = catEchange;
	}


	@Column(name="code_compte_ech")
	public String getCodeCompteEch() {
		return this.codeCompteEch;
	}

	public void setCodeCompteEch(String codeCompteEch) {
		this.codeCompteEch = codeCompteEch;
	}


	@Column(name="code_compte_obt")
	public String getCodeCompteObt() {
		return this.codeCompteObt;
	}

	public void setCodeCompteObt(String codeCompteObt) {
		this.codeCompteObt = codeCompteObt;
	}


	@Column(name="code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(name="code_produit")
	public String getCodeProduit() {
		return this.codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}


	public byte getCompenser() {
		return this.compenser;
	}

	public void setCompenser(byte compenser) {
		this.compenser = compenser;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_echange")
	public Date getDateEchange() {
		return this.dateEchange;
	}

	public void setDateEchange(Date dateEchange) {
		this.dateEchange = dateEchange;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_reglement")
	public Date getDateReglement() {
		return this.dateReglement;
	}

	public void setDateReglement(Date dateReglement) {
		this.dateReglement = dateReglement;
	}


	@Column(name="id_credit")
	public BigInteger getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(BigInteger idCredit) {
		this.idCredit = idCredit;
	}


	@Column(name="id_utilisateur")
	public BigInteger getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(BigInteger idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}


	@Column(name="montant_echange")
	public double getMontantEchange() {
		return this.montantEchange;
	}

	public void setMontantEchange(double montantEchange) {
		this.montantEchange = montantEchange;
	}


	public byte getRegler() {
		return this.regler;
	}

	public void setRegler(byte regler) {
		this.regler = regler;
	}


	@Column(name="type_echange")
	public String getTypeEchange() {
		return this.typeEchange;
	}

	public void setTypeEchange(String typeEchange) {
		this.typeEchange = typeEchange;
	}

}