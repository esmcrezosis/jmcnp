package com.esmc.mcnp.domain.entity.cm;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.ba.EuCapaAffecter;
import com.esmc.mcnp.domain.entity.bc.EuCnnc;
import com.esmc.mcnp.domain.entity.bc.EuCnp;
import com.esmc.mcnp.domain.entity.bc.EuDetailDomicilie;
import com.esmc.mcnp.domain.entity.bc.EuProduit;
import com.esmc.mcnp.domain.entity.obps.EuGcp;
import com.esmc.mcnp.domain.entity.oi.EuBnpCredit;
import com.esmc.mcnp.domain.entity.oi.EuCaps;
import com.esmc.mcnp.domain.entity.oi.EuDetailBnp;
import com.esmc.mcnp.domain.entity.others.*;
import com.esmc.mcnp.domain.entity.pc.EuRapprochement;
import com.esmc.mcnp.domain.entity.smcipn.EuSmc;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the eu_compte_credit database table.
 *
 */
@Entity
@Table(name = "eu_compte_credit")
@DynamicUpdate
@NamedQuery(name = "EuCompteCredit.findAll", query = "SELECT e FROM EuCompteCredit e")
public class EuCompteCredit implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idCredit;
	private String codeMembre;
	private Date dateOctroi;
	private String source;
	private Double montantPlace;
	private Double montantCredit;
	private Date datedeb;
	private Date datefin;
	private String compteSource;
	private String typeRecurrent;
	private Double duree;
	private Double prk;
	private String codeTypeCredit;
	private String typeProduit;
	private Integer frequenceCumul;
	private Integer affecter;
	private String renouveller;
	private Integer nbreRenouvel;
	private Boolean activer;
	private Integer domicilier;
	private String krr;
	private Integer idBps;
	private Integer bnp;
	private String codeBnp;
	private EuCompte euCompte;
	private EuProduit euProduit;
	private EuOperation euOperation;
	private List<EuBnpCredit> euBnpCredits;
	private List<EuCapaAffecter> euCapaAffecters;
	private List<EuCaps> euCaps;
	private List<EuCnnc> euCnncs;
	private List<EuCnp> euCnps;
	private List<EuCompteCreditCapa> euCompteCreditCapas;
	private List<EuDetailBnp> euDetailBnps;
	private List<EuDetailDomicilie> euDetailDomicilies;
	private List<EuGcp> euGcps;
	private List<EuRapprochement> euRapprochements;
	private List<EuSmc> euSmcs;
	private List<EuCompteCreditTs> euCompteCreditTss;

	public EuCompteCredit() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_credit", unique = true, nullable = false)
	public Long getIdCredit() {
		return this.idCredit;
	}

	public void setIdCredit(Long idCredit) {
		this.idCredit = idCredit;
	}

	public Integer getAffecter() {
		return this.affecter;
	}

	public void setAffecter(Integer affecter) {
		this.affecter = affecter;
	}

	public Integer getBnp() {
		return this.bnp;
	}

	public void setBnp(Integer bnp) {
		this.bnp = bnp;
	}

	@Column(name = "code_bnp", length = 255)
	public String getCodeBnp() {
		return this.codeBnp;
	}

	public void setCodeBnp(String codeBnp) {
		this.codeBnp = codeBnp;
	}

	@Column(name = "code_membre", length = 255)
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

	@Column(name = "code_type_credit", length = 60)
	public String getCodeTypeCredit() {
		return this.codeTypeCredit;
	}

	public void setCodeTypeCredit(String codeTypeCredit) {
		this.codeTypeCredit = codeTypeCredit;
	}

	@Column(name = "compte_source", length = 255)
	public String getCompteSource() {
		return this.compteSource;
	}

	public void setCompteSource(String compteSource) {
		this.compteSource = compteSource;
	}

	@JsonFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "date_octroi")
	public Date getDateOctroi() {
		return this.dateOctroi;
	}

	public void setDateOctroi(Date dateOctroi) {
		this.dateOctroi = dateOctroi;
	}

	@JsonFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	public Date getDatedeb() {
		return this.datedeb;
	}

	public void setDatedeb(Date datedeb) {
		this.datedeb = datedeb;
	}

	@JsonFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	public Date getDatefin() {
		return this.datefin;
	}

	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}

	public Integer getDomicilier() {
		return this.domicilier;
	}

	public void setDomicilier(Integer domicilier) {
		this.domicilier = domicilier;
	}

	@Column(length = 4)
	public String getKrr() {
		return this.krr;
	}

	public void setKrr(String krr) {
		this.krr = krr;
	}

	@Column(name = "montant_credit")
	public Double getMontantCredit() {
		return this.montantCredit;
	}

	public void setMontantCredit(Double montantCredit) {
		this.montantCredit = montantCredit;
	}

	@Column(name = "montant_place")
	public Double getMontantPlace() {
		return this.montantPlace;
	}

	public void setMontantPlace(Double montantPlace) {
		this.montantPlace = montantPlace;
	}

	@Column(name = "nbre_renouvel")
	public Integer getNbreRenouvel() {
		return this.nbreRenouvel;
	}

	public void setNbreRenouvel(Integer nbreRenouvel) {
		this.nbreRenouvel = nbreRenouvel;
	}

	public Double getPrk() {
		return this.prk;
	}

	public void setPrk(Double prk) {
		this.prk = prk;
	}

	@Column(length = 4)
	public String getRenouveller() {
		return this.renouveller;
	}

	public void setRenouveller(String renouveller) {
		this.renouveller = renouveller;
	}

	@Column(length = 255)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "type_recurrent")
	public String getTypeRecurrent() {
		return typeRecurrent;
	}

	public void setTypeRecurrent(String typeRecurrent) {
		this.typeRecurrent = typeRecurrent;
	}

	@Column(name = "type_produit")
	public String getTypeProduit() {
		return typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}

	@Column(name = "duree")
	public Double getDuree() {
		return duree;
	}

	public void setDuree(Double duree) {
		this.duree = duree;
	}

	@Column(name = "frequence_cumul")
	public Integer getFrequenceCumul() {
		return frequenceCumul;
	}

	public void setFrequenceCumul(Integer frequenceCumul) {
		this.frequenceCumul = frequenceCumul;
	}

	@Column(name = "id_bps")
	public Integer getIdBps() {
		return idBps;
	}

	public void setIdBps(Integer idBps) {
		this.idBps = idBps;
	}

	public Boolean getActiver() {
		return activer;
	}

	public void setActiver(Boolean activer) {
		this.activer = activer;
	}

	// bi-directional many-to-one association to EuCompte
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_compte")
	public EuCompte getEuCompte() {
		return this.euCompte;
	}

	public void setEuCompte(EuCompte euCompte) {
		this.euCompte = euCompte;
	}

	// bi-directional many-to-one association to EuProduit
	@ManyToOne
	@JoinColumn(name = "code_produit")
	public EuProduit getEuProduit() {
		return this.euProduit;
	}

	public void setEuProduit(EuProduit euProduit) {
		this.euProduit = euProduit;
	}

	// bi-directional many-to-one association to EuOperation
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_operation")
	public EuOperation getEuOperation() {
		return this.euOperation;
	}

	public void setEuOperation(EuOperation euOperation) {
		this.euOperation = euOperation;
	}

	// bi-directional many-to-one association to EuBnpCredit
	@JsonIgnore
	@OneToMany(mappedBy = "euCompteCredit")
	public List<EuBnpCredit> getEuBnpCredits() {
		return this.euBnpCredits;
	}

	public void setEuBnpCredits(List<EuBnpCredit> euBnpCredits) {
		this.euBnpCredits = euBnpCredits;
	}

	public EuBnpCredit addEuBnpCredit(EuBnpCredit euBnpCredit) {
		getEuBnpCredits().add(euBnpCredit);
		euBnpCredit.setEuCompteCredit(this);

		return euBnpCredit;
	}

	public EuBnpCredit removeEuBnpCredit(EuBnpCredit euBnpCredit) {
		getEuBnpCredits().remove(euBnpCredit);
		euBnpCredit.setEuCompteCredit(null);

		return euBnpCredit;
	}

	// bi-directional many-to-one association to EuCapaAffecter
	@JsonIgnore
	@OneToMany(mappedBy = "euCompteCredit")
	public List<EuCapaAffecter> getEuCapaAffecters() {
		return this.euCapaAffecters;
	}

	public void setEuCapaAffecters(List<EuCapaAffecter> euCapaAffecters) {
		this.euCapaAffecters = euCapaAffecters;
	}

	public EuCapaAffecter addEuCapaAffecter(EuCapaAffecter euCapaAffecter) {
		getEuCapaAffecters().add(euCapaAffecter);
		euCapaAffecter.setEuCompteCredit(this);

		return euCapaAffecter;
	}

	public EuCapaAffecter removeEuCapaAffecter(EuCapaAffecter euCapaAffecter) {
		getEuCapaAffecters().remove(euCapaAffecter);
		euCapaAffecter.setEuCompteCredit(null);

		return euCapaAffecter;
	}

	// bi-directional many-to-one association to EuCaps
	@JsonIgnore
	@OneToMany(mappedBy = "euCompteCredit")
	public List<EuCaps> getEuCaps() {
		return this.euCaps;
	}

	public void setEuCaps(List<EuCaps> euCaps) {
		this.euCaps = euCaps;
	}

	public EuCaps addEuCap(EuCaps euCap) {
		getEuCaps().add(euCap);
		euCap.setEuCompteCredit(this);

		return euCap;
	}

	public EuCaps removeEuCap(EuCaps euCap) {
		getEuCaps().remove(euCap);
		euCap.setEuCompteCredit(null);

		return euCap;
	}

	// bi-directional many-to-one association to EuCnnc
	@JsonIgnore
	@OneToMany(mappedBy = "euCompteCredit")
	public List<EuCnnc> getEuCnncs() {
		return this.euCnncs;
	}

	public void setEuCnncs(List<EuCnnc> euCnncs) {
		this.euCnncs = euCnncs;
	}

	public EuCnnc addEuCnnc(EuCnnc euCnnc) {
		getEuCnncs().add(euCnnc);
		euCnnc.setEuCompteCredit(this);

		return euCnnc;
	}

	public EuCnnc removeEuCnnc(EuCnnc euCnnc) {
		getEuCnncs().remove(euCnnc);
		euCnnc.setEuCompteCredit(null);

		return euCnnc;
	}

	// bi-directional many-to-one association to EuCnp
	@JsonIgnore
	@OneToMany(mappedBy = "euCompteCredit")
	public List<EuCnp> getEuCnps() {
		return this.euCnps;
	}

	public void setEuCnps(List<EuCnp> euCnps) {
		this.euCnps = euCnps;
	}

	public EuCnp addEuCnp(EuCnp euCnp) {
		getEuCnps().add(euCnp);
		euCnp.setEuCompteCredit(this);

		return euCnp;
	}

	public EuCnp removeEuCnp(EuCnp euCnp) {
		getEuCnps().remove(euCnp);
		euCnp.setEuCompteCredit(null);

		return euCnp;
	}

	// bi-directional many-to-one association to EuCompteCreditCapa
	@JsonIgnore
	@OneToMany(mappedBy = "euCompteCredit")
	public List<EuCompteCreditCapa> getEuCompteCreditCapas() {
		return this.euCompteCreditCapas;
	}

	public void setEuCompteCreditCapas(List<EuCompteCreditCapa> euCompteCreditCapas) {
		this.euCompteCreditCapas = euCompteCreditCapas;
	}

	public EuCompteCreditCapa addEuCompteCreditCapa(EuCompteCreditCapa euCompteCreditCapa) {
		getEuCompteCreditCapas().add(euCompteCreditCapa);
		euCompteCreditCapa.setEuCompteCredit(this);

		return euCompteCreditCapa;
	}

	public EuCompteCreditCapa removeEuCompteCreditCapa(EuCompteCreditCapa euCompteCreditCapa) {
		getEuCompteCreditCapas().remove(euCompteCreditCapa);
		euCompteCreditCapa.setEuCompteCredit(null);

		return euCompteCreditCapa;
	}

	// bi-directional many-to-one association to EuDetailBnp
	@JsonIgnore
	@OneToMany(mappedBy = "euCompteCredit")
	public List<EuDetailBnp> getEuDetailBnps() {
		return this.euDetailBnps;
	}

	public void setEuDetailBnps(List<EuDetailBnp> euDetailBnps) {
		this.euDetailBnps = euDetailBnps;
	}

	public EuDetailBnp addEuDetailBnp(EuDetailBnp euDetailBnp) {
		getEuDetailBnps().add(euDetailBnp);
		euDetailBnp.setEuCompteCredit(this);

		return euDetailBnp;
	}

	public EuDetailBnp removeEuDetailBnp(EuDetailBnp euDetailBnp) {
		getEuDetailBnps().remove(euDetailBnp);
		euDetailBnp.setEuCompteCredit(null);

		return euDetailBnp;
	}

	// bi-directional many-to-one association to EuDetailDomicilie
	@JsonIgnore
	@OneToMany(mappedBy = "euCompteCredit")
	public List<EuDetailDomicilie> getEuDetailDomicilies() {
		return this.euDetailDomicilies;
	}

	public void setEuDetailDomicilies(List<EuDetailDomicilie> euDetailDomicilies) {
		this.euDetailDomicilies = euDetailDomicilies;
	}

	public EuDetailDomicilie addEuDetailDomicily(EuDetailDomicilie euDetailDomicily) {
		getEuDetailDomicilies().add(euDetailDomicily);
		euDetailDomicily.setEuCompteCredit(this);

		return euDetailDomicily;
	}

	public EuDetailDomicilie removeEuDetailDomicily(EuDetailDomicilie euDetailDomicily) {
		getEuDetailDomicilies().remove(euDetailDomicily);
		euDetailDomicily.setEuCompteCredit(null);

		return euDetailDomicily;
	}

	// bi-directional many-to-one association to EuGcp
	@JsonIgnore
	@OneToMany(mappedBy = "euCompteCredit")
	public List<EuGcp> getEuGcps() {
		return this.euGcps;
	}

	public void setEuGcps(List<EuGcp> euGcps) {
		this.euGcps = euGcps;
	}

	public EuGcp addEuGcp(EuGcp euGcp) {
		getEuGcps().add(euGcp);
		euGcp.setEuCompteCredit(this);

		return euGcp;
	}

	public EuGcp removeEuGcp(EuGcp euGcp) {
		getEuGcps().remove(euGcp);
		euGcp.setEuCompteCredit(null);

		return euGcp;
	}

	// bi-directional many-to-one association to EuRapprochement
	@JsonIgnore
	@OneToMany(mappedBy = "euCompteCredit")
	public List<EuRapprochement> getEuRapprochements() {
		return this.euRapprochements;
	}

	public void setEuRapprochements(List<EuRapprochement> euRapprochements) {
		this.euRapprochements = euRapprochements;
	}

	public EuRapprochement addEuRapprochement(EuRapprochement euRapprochement) {
		getEuRapprochements().add(euRapprochement);
		euRapprochement.setEuCompteCredit(this);

		return euRapprochement;
	}

	public EuRapprochement removeEuRapprochement(EuRapprochement euRapprochement) {
		getEuRapprochements().remove(euRapprochement);
		euRapprochement.setEuCompteCredit(null);

		return euRapprochement;
	}

	// bi-directional many-to-one association to EuSmc
	@JsonIgnore
	@OneToMany(mappedBy = "euCompteCredit")
	public List<EuSmc> getEuSmcs() {
		return this.euSmcs;
	}

	public void setEuSmcs(List<EuSmc> euSmcs) {
		this.euSmcs = euSmcs;
	}

	public EuSmc addEuSmc(EuSmc euSmc) {
		getEuSmcs().add(euSmc);
		euSmc.setEuCompteCredit(this);

		return euSmc;
	}

	public EuSmc removeEuSmc(EuSmc euSmc) {
		getEuSmcs().remove(euSmc);
		euSmc.setEuCompteCredit(null);

		return euSmc;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "euCompteCredit")
	public List<EuCompteCreditTs> getEuCompteCreditTss() {
		return euCompteCreditTss;
	}

	public void setEuCompteCreditTss(List<EuCompteCreditTs> euCompteCreditTss) {
		this.euCompteCreditTss = euCompteCreditTss;
	}

}