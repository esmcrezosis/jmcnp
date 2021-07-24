package com.esmc.mcnp.model.bc;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_credit_code database table.
 * 
 */
@Entity
@Table(name="eu_credit_code")
@NamedQuery(name="EuCreditCode.findAll", query="SELECT e FROM EuCreditCode e")
public class EuCreditCode implements Serializable {
	private static final long serialVersionUID = 1L;
	private String creditCode;
	private String codeCompte;

	public EuCreditCode() {
	}


	@Id
	@Column(name="credit_code", unique=true, nullable=false, length=40)
	public String getCreditCode() {
		return this.creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}


	@Column(name="code_compte", nullable=false, length=180)
	public String getCodeCompte() {
		return this.codeCompte;
	}

	public void setCodeCompte(String codeCompte) {
		this.codeCompte = codeCompte;
	}

}