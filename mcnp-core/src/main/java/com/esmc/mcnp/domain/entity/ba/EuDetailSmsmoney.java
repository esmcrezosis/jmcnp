package com.esmc.mcnp.domain.entity.ba;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_detail_smsmoney database table.
 *
 */
@Entity
@Table(name="eu_detail_smsmoney")
@NamedQuery(name="EuDetailSmsmoney.findAll", query="SELECT e FROM EuDetailSmsmoney e")
public class EuDetailSmsmoney implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idDetailSmsmoney;
	private String codeMembre;
	private String creditcode;
	private Date dateAllocation;
	private Date dateSolde;
	private Long idUtilisateur;
	private double montRegle;
	private double montSms;
	private double montVendu;
	private double montantSolde;
	private double numBon;
	private String origineSms;
	private double soldeSms;
	private String typeSms;
	private EuMembreMorale euMembreMorale;
	private List<EuDetailVentesms> euDetailVentesms;

	public EuDetailSmsmoney() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_detail_smsmoney", unique=true, nullable=false)
	public Long getIdDetailSmsmoney() {
		return this.idDetailSmsmoney;
	}

	public void setIdDetailSmsmoney(Long idDetailSmsmoney) {
		this.idDetailSmsmoney = idDetailSmsmoney;
	}


	@Column(name="code_membre", length=200)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Column(length=40)
	public String getCreditcode() {
		return this.creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_allocation")
	public Date getDateAllocation() {
		return this.dateAllocation;
	}

	public void setDateAllocation(Date dateAllocation) {
		this.dateAllocation = dateAllocation;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_solde")
	public Date getDateSolde() {
		return this.dateSolde;
	}

	public void setDateSolde(Date dateSolde) {
		this.dateSolde = dateSolde;
	}


	@Column(name="id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	@Column(name="mont_regle", nullable=false)
	public double getMontRegle() {
		return this.montRegle;
	}

	public void setMontRegle(double montRegle) {
		this.montRegle = montRegle;
	}


	@Column(name="mont_sms")
	public double getMontSms() {
		return this.montSms;
	}

	public void setMontSms(double montSms) {
		this.montSms = montSms;
	}


	@Column(name="mont_vendu")
	public double getMontVendu() {
		return this.montVendu;
	}

	public void setMontVendu(double montVendu) {
		this.montVendu = montVendu;
	}


	@Column(name="montant_solde")
	public double getMontantSolde() {
		return this.montantSolde;
	}

	public void setMontantSolde(double montantSolde) {
		this.montantSolde = montantSolde;
	}


	@Column(name="num_bon")
	public double getNumBon() {
		return this.numBon;
	}

	public void setNumBon(double numBon) {
		this.numBon = numBon;
	}


	@Column(name="origine_sms", length=40)
	public String getOrigineSms() {
		return this.origineSms;
	}

	public void setOrigineSms(String origineSms) {
		this.origineSms = origineSms;
	}


	@Column(name="solde_sms")
	public double getSoldeSms() {
		return this.soldeSms;
	}

	public void setSoldeSms(double soldeSms) {
		this.soldeSms = soldeSms;
	}


	@Column(name="type_sms", nullable=false, length=180)
	public String getTypeSms() {
		return this.typeSms;
	}

	public void setTypeSms(String typeSms) {
		this.typeSms = typeSms;
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


	//bi-directional many-to-one association to EuDetailVentesms
	@OneToMany(mappedBy="euDetailSmsmoney")
	public List<EuDetailVentesms> getEuDetailVentesms() {
		return this.euDetailVentesms;
	}

	public void setEuDetailVentesms(List<EuDetailVentesms> euDetailVentesms) {
		this.euDetailVentesms = euDetailVentesms;
	}

	public EuDetailVentesms addEuDetailVentesm(EuDetailVentesms euDetailVentesm) {
		getEuDetailVentesms().add(euDetailVentesm);
		euDetailVentesm.setEuDetailSmsmoney(this);

		return euDetailVentesm;
	}

	public EuDetailVentesms removeEuDetailVentesm(EuDetailVentesms euDetailVentesm) {
		getEuDetailVentesms().remove(euDetailVentesm);
		euDetailVentesm.setEuDetailSmsmoney(null);

		return euDetailVentesm;
	}

}