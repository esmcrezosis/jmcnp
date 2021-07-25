package com.esmc.mcnp.model.ba;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;

/**
 * The persistent class for the eu_ancien_smsmoney database table.
 * 
 */
@Entity
@Table(name = "eu_ancien_smsmoney")
@NamedQuery(name = "EuAncienSmsmoney.findAll", query = "SELECT e FROM EuAncienSmsmoney e")
public class EuAncienSmsmoney implements Serializable {
	private static final long serialVersionUID = 1L;
	private int NEng;
	private String codeAgence;
	private double creditAmount;
	private String creditCode;
	private String currencyCode;
	private String dateTime;
	private String dateTimeConsumed;
	private String destAccount;
	private String destAccount_Consumed;
	private String fromAccount;
	private BigInteger id_Utilisateur;
	private int IDDateTime;
	private int IDDateTimeConsumed;
	private String motif;
	private String numRecu;
	private String sentTo;

	public EuAncienSmsmoney() {
	}

	@Id
	@Column(name = "Neng")
	public int getNEng() {
		return this.NEng;
	}

	public void setNEng(int NEng) {
		this.NEng = NEng;
	}

	@Column(name = "Code_Agence")
	public String getCodeAgence() {
		return this.codeAgence;
	}

	public void setCodeAgence(String codeAgence) {
		this.codeAgence = codeAgence;
	}

	@Column(name = "CreditAmount")
	public double getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	@Column(name = "CreditCode")
	public String getCreditCode() {
		return this.creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	@Column(name = "CurrencyCode")
	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Column(name = "DateTime")
	public String getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getDateTimeConsumed() {
		return this.dateTimeConsumed;
	}

	public void setDateTimeConsumed(String dateTimeConsumed) {
		this.dateTimeConsumed = dateTimeConsumed;
	}

	public String getDestAccount() {
		return this.destAccount;
	}

	public void setDestAccount(String destAccount) {
		this.destAccount = destAccount;
	}

	public String getDestAccount_Consumed() {
		return this.destAccount_Consumed;
	}

	public void setDestAccount_Consumed(String destAccount_Consumed) {
		this.destAccount_Consumed = destAccount_Consumed;
	}

	public String getFromAccount() {
		return this.fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public BigInteger getId_Utilisateur() {
		return this.id_Utilisateur;
	}

	public void setId_Utilisateur(BigInteger id_Utilisateur) {
		this.id_Utilisateur = id_Utilisateur;
	}

	public int getIDDateTime() {
		return this.IDDateTime;
	}

	public void setIDDateTime(int IDDateTime) {
		this.IDDateTime = IDDateTime;
	}

	public int getIDDateTimeConsumed() {
		return this.IDDateTimeConsumed;
	}

	public void setIDDateTimeConsumed(int IDDateTimeConsumed) {
		this.IDDateTimeConsumed = IDDateTimeConsumed;
	}

	public String getMotif() {
		return this.motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	@Column(name = "num_recu")
	public String getNumRecu() {
		return this.numRecu;
	}

	public void setNumRecu(String numRecu) {
		this.numRecu = numRecu;
	}

	public String getSentTo() {
		return this.sentTo;
	}

	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}

}