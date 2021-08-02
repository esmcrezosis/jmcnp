package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_smsmoney database table.
 *
 */
@Entity
@Table(name="eu_smsmoney")
@NamedQuery(name="EuSmsmoney.findAll", query="SELECT e FROM EuSmsmoney e")
public class EuSmsmoney implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long neng;
	private String codeAgence;
	private String codeagence;
	private double creditamount;
	private String creditcode;
	private String currencycode;
	private String datetime;
	private String datetimeconsumed;
	private String destaccount;
	private String destaccountConsumed;
	private String fromaccount;
	private Long idUtilisateur;
	private Integer iddatetime;
	private Integer iddatetimeconsumed;
	private String motif;
	private String numRecu;
	private String sentto;
	private String utilisateur;

	public EuSmsmoney() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public Long getNeng() {
		return this.neng;
	}

	public void setNeng(Long neng) {
		this.neng = neng;
	}


	@Column(name="code_agence", length=100)
	public String getCodeAgence() {
		return this.codeAgence;
	}

	public void setCodeAgence(String codeAgence) {
		this.codeAgence = codeAgence;
	}


	@Column(length=100)
	public String getCodeagence() {
		return this.codeagence;
	}

	public void setCodeagence(String codeagence) {
		this.codeagence = codeagence;
	}


	public double getCreditamount() {
		return this.creditamount;
	}

	public void setCreditamount(double creditamount) {
		this.creditamount = creditamount;
	}


	@Column(length=60)
	public String getCreditcode() {
		return this.creditcode;
	}

	public void setCreditcode(String creditcode) {
		this.creditcode = creditcode;
	}


	@Column(length=20)
	public String getCurrencycode() {
		return this.currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}


	@Column(length=80)
	public String getDatetime() {
		return this.datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}


	@Column(length=200)
	public String getDatetimeconsumed() {
		return this.datetimeconsumed;
	}

	public void setDatetimeconsumed(String datetimeconsumed) {
		this.datetimeconsumed = datetimeconsumed;
	}


	@Column(length=100)
	public String getDestaccount() {
		return this.destaccount;
	}

	public void setDestaccount(String destaccount) {
		this.destaccount = destaccount;
	}


	@Column(name="destaccount_consumed", length=100)
	public String getDestaccountConsumed() {
		return this.destaccountConsumed;
	}

	public void setDestaccountConsumed(String destaccountConsumed) {
		this.destaccountConsumed = destaccountConsumed;
	}


	@Column(length=100)
	public String getFromaccount() {
		return this.fromaccount;
	}

	public void setFromaccount(String fromaccount) {
		this.fromaccount = fromaccount;
	}


	@Column(name="id_utilisateur")
	public Long getIdUtilisateur() {
		return this.idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}


	public Integer getIddatetime() {
		return this.iddatetime;
	}

	public void setIddatetime(Integer iddatetime) {
		this.iddatetime = iddatetime;
	}


	public Integer getIddatetimeconsumed() {
		return this.iddatetimeconsumed;
	}

	public void setIddatetimeconsumed(Integer iddatetimeconsumed) {
		this.iddatetimeconsumed = iddatetimeconsumed;
	}


	@Column(length=80)
	public String getMotif() {
		return this.motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}


	@Column(name="num_recu", length=200)
	public String getNumRecu() {
		return this.numRecu;
	}

	public void setNumRecu(String numRecu) {
		this.numRecu = numRecu;
	}


	@Column(length=80)
	public String getSentto() {
		return this.sentto;
	}

	public void setSentto(String sentto) {
		this.sentto = sentto;
	}


	@Column(length=100)
	public String getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}

}