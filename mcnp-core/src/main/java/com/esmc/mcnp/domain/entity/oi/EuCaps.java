package com.esmc.mcnp.domain.entity.oi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.others.EuOperation;


/**
 * The persistent class for the eu_caps database table.
 * 
 */
@Entity
@Table(name="eu_caps")
@NamedQuery(name="EuCaps.findAll", query="SELECT e FROM EuCaps e")
public class EuCaps implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeCaps;
	private Integer cpsUtiliser;
	private Date dateCaps;
	private Integer flUtiliser;
	private Integer fsUtiliser;
	private Long idUtilisateur;
	private Integer indexer;
	private double montCaps;
	private double montFs;
	private double montPanuFs;
	private double panu;
	private int periode;
	private int reconstFs;
	private String rembourser;
	private String typeCaps;
	private String typeOp;
	private EuCompteCredit euCompteCredit;
	private EuOperation euOperation;
	private EuTypeBnp euTypeBnp;
	private EuMembre euMembre1;
	private EuMembre euMembre2;
	private EuMembreMorale euMembreMorale;

	public EuCaps() {
	}


	@Id
	@Column(name="code_caps", unique=true, nullable=false, length=200)
	public String getCodeCaps() {
		return this.codeCaps;
	}

	public void setCodeCaps(String codeCaps) {
		this.codeCaps = codeCaps;
	}


	@Column(name="cps_utiliser")
	public Integer getCpsUtiliser() {
		return this.cpsUtiliser;
	}

	public void setCpsUtiliser(Integer cpsUtiliser) {
		this.cpsUtiliser = cpsUtiliser;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_caps")
	public Date getDateCaps() {
		return this.dateCaps;
	}

	public void setDateCaps(Date dateCaps) {
		this.dateCaps = dateCaps;
	}


	@Column(name="fl_utiliser")
	public Integer getFlUtiliser() {
		return this.flUtiliser;
	}

	public void setFlUtiliser(Integer flUtiliser) {
		this.flUtiliser = flUtiliser;
	}


	@Column(name="fs_utiliser")
	public Integer getFsUtiliser() {
		return this.fsUtiliser;
	}

	public void setFsUtiliser(Integer fsUtiliser) {
		this.fsUtiliser = fsUtiliser;
	}


	@Column(name="id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	public Integer getIndexer() {
		return this.indexer;
	}

	public void setIndexer(Integer indexer) {
		this.indexer = indexer;
	}


	@Column(name="mont_caps")
	public double getMontCaps() {
		return this.montCaps;
	}

	public void setMontCaps(double montCaps) {
		this.montCaps = montCaps;
	}


	@Column(name="mont_fs")
	public double getMontFs() {
		return this.montFs;
	}

	public void setMontFs(double montFs) {
		this.montFs = montFs;
	}


	@Column(name="mont_panu_fs")
	public double getMontPanuFs() {
		return this.montPanuFs;
	}

	public void setMontPanuFs(double montPanuFs) {
		this.montPanuFs = montPanuFs;
	}


	public double getPanu() {
		return this.panu;
	}

	public void setPanu(double panu) {
		this.panu = panu;
	}


	public Integer getPeriode() {
		return this.periode;
	}

	public void setPeriode(Integer periode) {
		this.periode = periode;
	}


	@Column(name="reconst_fs")
	public Integer getReconstFs() {
		return this.reconstFs;
	}

	public void setReconstFs(Integer reconstFs) {
		this.reconstFs = reconstFs;
	}


	@Column(length=4)
	public String getRembourser() {
		return this.rembourser;
	}

	public void setRembourser(String rembourser) {
		this.rembourser = rembourser;
	}


	@Column(name="type_caps", length=60)
	public String getTypeCaps() {
		return this.typeCaps;
	}

	public void setTypeCaps(String typeCaps) {
		this.typeCaps = typeCaps;
	}


	@Column(name="type_op", length=10)
	public String getTypeOp() {
		return this.typeOp;
	}

	public void setTypeOp(String typeOp) {
		this.typeOp = typeOp;
	}


	//bi-directional many-to-one association to EuCompteCredit
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_credit")
	public EuCompteCredit getEuCompteCredit() {
		return this.euCompteCredit;
	}

	public void setEuCompteCredit(EuCompteCredit euCompteCredit) {
		this.euCompteCredit = euCompteCredit;
	}


	//bi-directional many-to-one association to EuOperation
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_operation")
	public EuOperation getEuOperation() {
		return this.euOperation;
	}

	public void setEuOperation(EuOperation euOperation) {
		this.euOperation = euOperation;
	}


	//bi-directional many-to-one association to EuTypeBnp
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_type_bnp")
	public EuTypeBnp getEuTypeBnp() {
		return this.euTypeBnp;
	}

	public void setEuTypeBnp(EuTypeBnp euTypeBnp) {
		this.euTypeBnp = euTypeBnp;
	}


	//bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_app")
	public EuMembre getEuMembre1() {
		return this.euMembre1;
	}

	public void setEuMembre1(EuMembre euMembre1) {
		this.euMembre1 = euMembre1;
	}


	//bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_benef")
	public EuMembre getEuMembre2() {
		return this.euMembre2;
	}

	public void setEuMembre2(EuMembre euMembre2) {
		this.euMembre2 = euMembre2;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_morale_app")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}

}