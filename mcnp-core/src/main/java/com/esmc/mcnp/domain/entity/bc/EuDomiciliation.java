package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.acteur.EuLouer;
import com.esmc.mcnp.domain.entity.ba.EuCapaAffecter;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.others.EuProposition;
import com.esmc.mcnp.domain.entity.pc.EuRapprochement;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.esmc.mcnp.domain.entity.smcipn.EuSmcipnpwi;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_domiciliation database table.
 *
 */
@Entity
@Table(name="eu_domiciliation")
@NamedQuery(name="EuDomiciliation.findAll", query="SELECT e FROM EuDomiciliation e")
public class EuDomiciliation implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codeDomicilier;
	private String accorder;
	private String catRessource;
	private String codeMembreAssureur;
	private String codeSmcipnp;
	private Date dateDomiciliation;
	private Date dateEchue;
	private String domicilier;
	private Integer dureeRenouvellement;
	private double montantDomicilier;
	private double montantSubvent;
	private Integer resteDuree;
	private String typeDomiciliation;
	private List<EuCapaAffecter> euCapaAffecters;
	private List<EuCnp> euCnps;
	private List<EuDetailDomicilie> euDetailDomicilies;
	private EuMembreMorale euMembreMorale;
	private EuUtilisateur euUtilisateur;
	private EuProposition euProposition;
	private EuSmcipnpwi euSmcipnpwi;
	private List<EuLouer> euLouers;
	private List<EuRapprochement> euRapprochements;

	public EuDomiciliation() {
	}


	@Id
	@Column(name="code_domicilier", unique=true, nullable=false, length=200)
	public String getCodeDomicilier() {
		return this.codeDomicilier;
	}

	public void setCodeDomicilier(String codeDomicilier) {
		this.codeDomicilier = codeDomicilier;
	}


	@Column(length=4)
	public String getAccorder() {
		return this.accorder;
	}

	public void setAccorder(String accorder) {
		this.accorder = accorder;
	}


	@Column(name="cat_ressource", length=40)
	public String getCatRessource() {
		return this.catRessource;
	}

	public void setCatRessource(String catRessource) {
		this.catRessource = catRessource;
	}


	@Column(name="code_membre_assureur", length=100)
	public String getCodeMembreAssureur() {
		return this.codeMembreAssureur;
	}

	public void setCodeMembreAssureur(String codeMembreAssureur) {
		this.codeMembreAssureur = codeMembreAssureur;
	}


	@Column(name="code_smcipnp", length=200)
	public String getCodeSmcipnp() {
		return this.codeSmcipnp;
	}

	public void setCodeSmcipnp(String codeSmcipnp) {
		this.codeSmcipnp = codeSmcipnp;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_domiciliation")
	public Date getDateDomiciliation() {
		return this.dateDomiciliation;
	}

	public void setDateDomiciliation(Date dateDomiciliation) {
		this.dateDomiciliation = dateDomiciliation;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_echue")
	public Date getDateEchue() {
		return this.dateEchue;
	}

	public void setDateEchue(Date dateEchue) {
		this.dateEchue = dateEchue;
	}


	@Column(length=4)
	public String getDomicilier() {
		return this.domicilier;
	}

	public void setDomicilier(String domicilier) {
		this.domicilier = domicilier;
	}


	@Column(name="duree_renouvellement")
	public Integer getDureeRenouvellement() {
		return this.dureeRenouvellement;
	}

	public void setDureeRenouvellement(Integer dureeRenouvellement) {
		this.dureeRenouvellement = dureeRenouvellement;
	}


	@Column(name="montant_domicilier")
	public double getMontantDomicilier() {
		return this.montantDomicilier;
	}

	public void setMontantDomicilier(double montantDomicilier) {
		this.montantDomicilier = montantDomicilier;
	}


	@Column(name="montant_subvent")
	public double getMontantSubvent() {
		return this.montantSubvent;
	}

	public void setMontantSubvent(double montantSubvent) {
		this.montantSubvent = montantSubvent;
	}


	@Column(name="reste_duree")
	public Integer getResteDuree() {
		return this.resteDuree;
	}

	public void setResteDuree(Integer resteDuree) {
		this.resteDuree = resteDuree;
	}


	@Column(name="type_domiciliation", length=40)
	public String getTypeDomiciliation() {
		return this.typeDomiciliation;
	}

	public void setTypeDomiciliation(String typeDomiciliation) {
		this.typeDomiciliation = typeDomiciliation;
	}


	//bi-directional many-to-one association to EuCapaAffecter
	@OneToMany(mappedBy="euDomiciliation")
	public List<EuCapaAffecter> getEuCapaAffecters() {
		return this.euCapaAffecters;
	}

	public void setEuCapaAffecters(List<EuCapaAffecter> euCapaAffecters) {
		this.euCapaAffecters = euCapaAffecters;
	}

	public EuCapaAffecter addEuCapaAffecter(EuCapaAffecter euCapaAffecter) {
		getEuCapaAffecters().add(euCapaAffecter);
		euCapaAffecter.setEuDomiciliation(this);

		return euCapaAffecter;
	}

	public EuCapaAffecter removeEuCapaAffecter(EuCapaAffecter euCapaAffecter) {
		getEuCapaAffecters().remove(euCapaAffecter);
		euCapaAffecter.setEuDomiciliation(null);

		return euCapaAffecter;
	}


	//bi-directional many-to-one association to EuCnp
	@OneToMany(mappedBy="euDomiciliation")
	public List<EuCnp> getEuCnps() {
		return this.euCnps;
	}

	public void setEuCnps(List<EuCnp> euCnps) {
		this.euCnps = euCnps;
	}

	public EuCnp addEuCnp(EuCnp euCnp) {
		getEuCnps().add(euCnp);
		euCnp.setEuDomiciliation(this);

		return euCnp;
	}

	public EuCnp removeEuCnp(EuCnp euCnp) {
		getEuCnps().remove(euCnp);
		euCnp.setEuDomiciliation(null);

		return euCnp;
	}


	//bi-directional many-to-one association to EuDetailDomicilie
	@OneToMany(mappedBy="euDomiciliation")
	public List<EuDetailDomicilie> getEuDetailDomicilies() {
		return this.euDetailDomicilies;
	}

	public void setEuDetailDomicilies(List<EuDetailDomicilie> euDetailDomicilies) {
		this.euDetailDomicilies = euDetailDomicilies;
	}

	public EuDetailDomicilie addEuDetailDomicily(EuDetailDomicilie euDetailDomicily) {
		getEuDetailDomicilies().add(euDetailDomicily);
		euDetailDomicily.setEuDomiciliation(this);

		return euDetailDomicily;
	}

	public EuDetailDomicilie removeEuDetailDomicily(EuDetailDomicilie euDetailDomicily) {
		getEuDetailDomicilies().remove(euDetailDomicily);
		euDetailDomicily.setEuDomiciliation(null);

		return euDetailDomicily;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_beneficiaire")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}


	//bi-directional many-to-one association to EuUtilisateur
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_utilisateur")
	public EuUtilisateur getEuUtilisateur() {
		return this.euUtilisateur;
	}

	public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
		this.euUtilisateur = euUtilisateur;
	}


	//bi-directional many-to-one association to EuProposition
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_proposition")
	public EuProposition getEuProposition() {
		return this.euProposition;
	}

	public void setEuProposition(EuProposition euProposition) {
		this.euProposition = euProposition;
	}


	//bi-directional many-to-one association to EuSmcipnpwi
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_smcipn")
	public EuSmcipnpwi getEuSmcipnpwi() {
		return this.euSmcipnpwi;
	}

	public void setEuSmcipnpwi(EuSmcipnpwi euSmcipnpwi) {
		this.euSmcipnpwi = euSmcipnpwi;
	}


	//bi-directional many-to-one association to EuLouer
	@OneToMany(mappedBy="euDomiciliation")
	public List<EuLouer> getEuLouers() {
		return this.euLouers;
	}

	public void setEuLouers(List<EuLouer> euLouers) {
		this.euLouers = euLouers;
	}

	public EuLouer addEuLouer(EuLouer euLouer) {
		getEuLouers().add(euLouer);
		euLouer.setEuDomiciliation(this);

		return euLouer;
	}

	public EuLouer removeEuLouer(EuLouer euLouer) {
		getEuLouers().remove(euLouer);
		euLouer.setEuDomiciliation(null);

		return euLouer;
	}


	//bi-directional many-to-one association to EuRapprochement
	@OneToMany(mappedBy="euDomiciliation")
	public List<EuRapprochement> getEuRapprochements() {
		return this.euRapprochements;
	}

	public void setEuRapprochements(List<EuRapprochement> euRapprochements) {
		this.euRapprochements = euRapprochements;
	}

	public EuRapprochement addEuRapprochement(EuRapprochement euRapprochement) {
		getEuRapprochements().add(euRapprochement);
		euRapprochement.setEuDomiciliation(this);

		return euRapprochement;
	}

	public EuRapprochement removeEuRapprochement(EuRapprochement euRapprochement) {
		getEuRapprochements().remove(euRapprochement);
		euRapprochement.setEuDomiciliation(null);

		return euRapprochement;
	}

}