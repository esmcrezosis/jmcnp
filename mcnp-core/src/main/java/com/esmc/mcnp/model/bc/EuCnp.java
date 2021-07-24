package com.esmc.mcnp.model.bc;

import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.obps.EuGcp;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_cnp database table.
 *
 */
@Entity
@Table(name="eu_cnp")
@DynamicUpdate
@NamedQuery(name="EuCnp.findAll", query="SELECT e FROM EuCnp e")
public class EuCnp implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idCnp;
	private Date dateCnp;
	private double montCredit;
	private double montDebit;
	private String origineCnp;
	private double soldeCnp;
	private String sourceCredit;
	private double transfertGcp;
	private String typeCnp;
	private EuCapa euCapa;
	private EuGcp euGcp;
	private EuDomiciliation euDomiciliation;
	private EuCompteCredit euCompteCredit;
	private List<EuCnpEntree> euCnpEntrees;

	public EuCnp() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_cnp", unique=true, nullable=false)
	public Long getIdCnp() {
		return this.idCnp;
	}

	public void setIdCnp(Long idCnp) {
		this.idCnp = idCnp;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_cnp")
	public Date getDateCnp() {
		return this.dateCnp;
	}

	public void setDateCnp(Date dateCnp) {
		this.dateCnp = dateCnp;
	}


	@Column(name="mont_credit")
	public double getMontCredit() {
		return this.montCredit;
	}

	public void setMontCredit(double montCredit) {
		this.montCredit = montCredit;
	}


	@Column(name="mont_debit")
	public double getMontDebit() {
		return this.montDebit;
	}

	public void setMontDebit(double montDebit) {
		this.montDebit = montDebit;
	}


	@Column(name="origine_cnp", length=60)
	public String getOrigineCnp() {
		return this.origineCnp;
	}

	public void setOrigineCnp(String origineCnp) {
		this.origineCnp = origineCnp;
	}


	@Column(name="solde_cnp")
	public double getSoldeCnp() {
		return this.soldeCnp;
	}

	public void setSoldeCnp(double soldeCnp) {
		this.soldeCnp = soldeCnp;
	}


	@Column(name="source_credit", length=200)
	public String getSourceCredit() {
		return this.sourceCredit;
	}

	public void setSourceCredit(String sourceCredit) {
		this.sourceCredit = sourceCredit;
	}


	@Column(name="transfert_gcp", nullable=false)
	public double getTransfertGcp() {
		return this.transfertGcp;
	}

	public void setTransfertGcp(double transfertGcp) {
		this.transfertGcp = transfertGcp;
	}


	@Column(name="type_cnp", length=60)
	public String getTypeCnp() {
		return this.typeCnp;
	}

	public void setTypeCnp(String typeCnp) {
		this.typeCnp = typeCnp;
	}


	//bi-directional many-to-one association to EuCapa
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_capa")
	public EuCapa getEuCapa() {
		return this.euCapa;
	}

	public void setEuCapa(EuCapa euCapa) {
		this.euCapa = euCapa;
	}


	//bi-directional many-to-one association to EuDomiciliation
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_domicilier")
	public EuDomiciliation getEuDomiciliation() {
		return this.euDomiciliation;
	}
	
	
	//bi-directional many-to-one association to EuGcp
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name="id_gcp")
	public EuGcp getEuGcp() {
		return euGcp;
	}


	public void setEuGcp(EuGcp euGcp) {
		this.euGcp = euGcp;
	}


	public void setEuDomiciliation(EuDomiciliation euDomiciliation) {
		this.euDomiciliation = euDomiciliation;
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


	//bi-directional many-to-one association to EuCnpEntree
	@OneToMany(mappedBy="euCnp")
	public List<EuCnpEntree> getEuCnpEntrees() {
		return this.euCnpEntrees;
	}

	public void setEuCnpEntrees(List<EuCnpEntree> euCnpEntrees) {
		this.euCnpEntrees = euCnpEntrees;
	}

	public EuCnpEntree addEuCnpEntree(EuCnpEntree euCnpEntree) {
		getEuCnpEntrees().add(euCnpEntree);
		euCnpEntree.setEuCnp(this);

		return euCnpEntree;
	}

	public EuCnpEntree removeEuCnpEntree(EuCnpEntree euCnpEntree) {
		getEuCnpEntrees().remove(euCnpEntree);
		euCnpEntree.setEuCnp(null);

		return euCnpEntree;
	}

}