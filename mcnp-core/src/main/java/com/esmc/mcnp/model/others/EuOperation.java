package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.obps.EuCreditConsommer;
import com.esmc.mcnp.model.oi.EuBnp;
import com.esmc.mcnp.model.oi.EuCaps;
import com.esmc.mcnp.model.security.EuUtilisateur;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_operation database table.
 *
 */
@Entity
@Table(name="eu_operation")
@NamedQuery(name="EuOperation.findAll", query="SELECT e FROM EuOperation e")
public class EuOperation implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idOperation;
	private String codeProduit;
	private Date dateOp;
	private Date heureOp;
	private String libOp;
	private double montantOp;
	private String typeOp;
	private List<EuBnp> euBnps;
	private List<EuCaps> euCaps;
	private List<EuCompteCredit> euCompteCredits;
	private List<EuCreditConsommer> euCreditConsommers;
	private EuMembreMorale euMembreMorale;
	private EuMembre euMembre;
	private EuUtilisateur euUtilisateur;
	private EuCategorieCompte euCategorieCompte;

	public EuOperation() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_operation", unique=true, nullable=false)
	public Long getIdOperation() {
		return this.idOperation;
	}

	public void setIdOperation(Long idOperation) {
		this.idOperation = idOperation;
	}


	@Column(name="code_produit", length=100)
	public String getCodeProduit() {
		return this.codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_op")
	public Date getDateOp() {
		return this.dateOp;
	}

	public void setDateOp(Date dateOp) {
		this.dateOp = dateOp;
	}


	@Temporal(TemporalType.TIME)
	@Column(name="heure_op")
	public Date getHeureOp() {
		return this.heureOp;
	}

	public void setHeureOp(Date heureOp) {
		this.heureOp = heureOp;
	}


	@Column(name="lib_op", length=300)
	public String getLibOp() {
		return this.libOp;
	}

	public void setLibOp(String libOp) {
		this.libOp = libOp;
	}


	@Column(name="montant_op")
	public double getMontantOp() {
		return this.montantOp;
	}

	public void setMontantOp(double montantOp) {
		this.montantOp = montantOp;
	}


	@Column(name="type_op", length=180)
	public String getTypeOp() {
		return this.typeOp;
	}

	public void setTypeOp(String typeOp) {
		this.typeOp = typeOp;
	}


	//bi-directional many-to-one association to EuBnp
	@OneToMany(mappedBy="euOperation")
	public List<EuBnp> getEuBnps() {
		return this.euBnps;
	}

	public void setEuBnps(List<EuBnp> euBnps) {
		this.euBnps = euBnps;
	}

	public EuBnp addEuBnp(EuBnp euBnp) {
		getEuBnps().add(euBnp);
		euBnp.setEuOperation(this);

		return euBnp;
	}

	public EuBnp removeEuBnp(EuBnp euBnp) {
		getEuBnps().remove(euBnp);
		euBnp.setEuOperation(null);

		return euBnp;
	}


	//bi-directional many-to-one association to EuCaps
	@OneToMany(mappedBy="euOperation")
	public List<EuCaps> getEuCaps() {
		return this.euCaps;
	}

	public void setEuCaps(List<EuCaps> euCaps) {
		this.euCaps = euCaps;
	}

	public EuCaps addEuCap(EuCaps euCap) {
		getEuCaps().add(euCap);
		euCap.setEuOperation(this);

		return euCap;
	}

	public EuCaps removeEuCap(EuCaps euCap) {
		getEuCaps().remove(euCap);
		euCap.setEuOperation(null);

		return euCap;
	}


	//bi-directional many-to-one association to EuCompteCredit
	@OneToMany(mappedBy="euOperation")
	public List<EuCompteCredit> getEuCompteCredits() {
		return this.euCompteCredits;
	}

	public void setEuCompteCredits(List<EuCompteCredit> euCompteCredits) {
		this.euCompteCredits = euCompteCredits;
	}

	public EuCompteCredit addEuCompteCredit(EuCompteCredit euCompteCredit) {
		getEuCompteCredits().add(euCompteCredit);
		euCompteCredit.setEuOperation(this);

		return euCompteCredit;
	}

	public EuCompteCredit removeEuCompteCredit(EuCompteCredit euCompteCredit) {
		getEuCompteCredits().remove(euCompteCredit);
		euCompteCredit.setEuOperation(null);

		return euCompteCredit;
	}


	//bi-directional many-to-one association to EuCreditConsommer
	@OneToMany(mappedBy="euOperation")
	public List<EuCreditConsommer> getEuCreditConsommers() {
		return this.euCreditConsommers;
	}

	public void setEuCreditConsommers(List<EuCreditConsommer> euCreditConsommers) {
		this.euCreditConsommers = euCreditConsommers;
	}

	public EuCreditConsommer addEuCreditConsommer(EuCreditConsommer euCreditConsommer) {
		getEuCreditConsommers().add(euCreditConsommer);
		euCreditConsommer.setEuOperation(this);

		return euCreditConsommer;
	}

	public EuCreditConsommer removeEuCreditConsommer(EuCreditConsommer euCreditConsommer) {
		getEuCreditConsommers().remove(euCreditConsommer);
		euCreditConsommer.setEuOperation(null);

		return euCreditConsommer;
	}


	//bi-directional many-to-one association to EuMembreMorale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre_morale")
	public EuMembreMorale getEuMembreMorale() {
		return this.euMembreMorale;
	}

	public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
		this.euMembreMorale = euMembreMorale;
	}


	//bi-directional many-to-one association to EuMembre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_membre")
	public EuMembre getEuMembre() {
		return this.euMembre;
	}

	public void setEuMembre(EuMembre euMembre) {
		this.euMembre = euMembre;
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


	//bi-directional many-to-one association to EuCategorieCompte
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_cat")
	public EuCategorieCompte getEuCategorieCompte() {
		return this.euCategorieCompte;
	}

	public void setEuCategorieCompte(EuCategorieCompte euCategorieCompte) {
		this.euCategorieCompte = euCategorieCompte;
	}

}