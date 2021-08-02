package com.esmc.mcnp.domain.entity.ba;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;

import java.util.Date;


/**
 * The persistent class for the eu_detail_ventesms database table.
 *
 */
@Entity
@Table(name="eu_detail_ventesms")
@NamedQuery(name="EuDetailVentesms.findAll", query="SELECT e FROM EuDetailVentesms e")
public class EuDetailVentesms implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idDetailVtsms;
	private String codeMembre;
	private String codeProduit;
	private String creditcode;
	private Date dateVente;
	private Long idUtilisateur;
	private double montVente;
	private String origineCompte;
	private String typeTransfert;
	private EuMembreMorale euMembreMorale;
	private EuDetailSmsmoney euDetailSmsmoney;

	public EuDetailVentesms() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_detail_vtsms", unique=true, nullable=false)
	public Long getIdDetailVtsms() {
		return this.idDetailVtsms;
	}

	public void setIdDetailVtsms(Long idDetailVtsms) {
		this.idDetailVtsms = idDetailVtsms;
	}


	@Column(name="code_membre", length=180)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(name="code_produit", nullable=false, length=200)
	public String getCodeProduit() {
		return this.codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}


	@Column(length=200)
	public String getCreditcode() {
		return this.creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_vente")
	public Date getDateVente() {
		return this.dateVente;
	}

	public void setDateVente(Date dateVente) {
		this.dateVente = dateVente;
	}


	@Column(name="id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="mont_vente")
	public double getMontVente() {
		return this.montVente;
	}

	public void setMontVente(double montVente) {
		this.montVente = montVente;
	}


	@Column(name="origine_compte", length=45)
	public String getOrigineCompte() {
		return this.origineCompte;
	}

	public void setOrigineCompte(String origineCompte) {
		this.origineCompte = origineCompte;
	}


	@Column(name="type_transfert", length=100)
	public String getTypeTransfert() {
		return this.typeTransfert;
	}

	public void setTypeTransfert(String typeTransfert) {
		this.typeTransfert = typeTransfert;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_dist")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}


	//bi-directional many-to-one association to EuDetailSmsmoney
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_detail_smsmoney")
	public EuDetailSmsmoney getEuDetailSmsmoney() {
		return this.euDetailSmsmoney;
	}

	public void setEuDetailSmsmoney(EuDetailSmsmoney euDetailSmsmoney) {
		this.euDetailSmsmoney = euDetailSmsmoney;
	}

}