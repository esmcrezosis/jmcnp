package com.esmc.mcnp.domain.entity.smcipn;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

import com.esmc.mcnp.domain.entity.smcipn.EuSmcipnpwi;

/**
 * The persistent class for the eu_gcsc database table.
 *
 */
@Entity
@Table(name = "eu_gcsc")
@DynamicUpdate
@NamedQuery(name = "EuGcsc.findAll", query = "SELECT e FROM EuGcsc e")
public class EuGcsc implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idGcsc;
    private String codeDomicilier;
    private double credit;
    private double debit;
    private double solde;
    private Date dateGcsc;
    private String codeMembre;
    private EuSmcipnpwi euSmcipnpwi;
    private String codeTegc;

    public EuGcsc() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gcsc", unique = true, nullable = false)
    public Long getIdGcsc() {
        return this.idGcsc;
    }

    public void setIdGcsc(Long idGcsc) {
        this.idGcsc = idGcsc;
    }

    @Column(name = "code_domicilier", length = 200)
    public String getCodeDomicilier() {
        return this.codeDomicilier;
    }

    public void setCodeDomicilier(String codeDomicilier) {
        this.codeDomicilier = codeDomicilier;
    }

    public double getCredit() {
        return this.credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getDebit() {
        return this.debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getSolde() {
        return this.solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    @Column(name = "date_gcsc")
    public Date getDateGcsc() {
		return dateGcsc;
	}

	public void setDateGcsc(Date dateGcsc) {
		this.dateGcsc = dateGcsc;
	}

	// bi-directional many-to-one association to EuMembreMorale
    @Column(name = "code_membre")
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    // bi-directional many-to-one association to EuSmcipnpwi
    @ManyToOne
    @JoinColumn(name = "code_smcipn")
    public EuSmcipnpwi getEuSmcipnpwi() {
        return this.euSmcipnpwi;
    }

    public void setEuSmcipnpwi(EuSmcipnpwi euSmcipnpwi) {
        this.euSmcipnpwi = euSmcipnpwi;
    }

    @Column(name = "code_tegc")
    public String getCodeTegc() {
        return codeTegc;
    }

    public void setCodeTegc(String codeTegc) {
        this.codeTegc = codeTegc;
    }

}
