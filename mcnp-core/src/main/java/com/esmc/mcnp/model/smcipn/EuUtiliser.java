package com.esmc.mcnp.model.smcipn;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the eu_utiliser database table.
 *
 */
@Entity
@Table(name = "eu_utiliser")
@NamedQuery(name = "EuUtiliser.findAll", query = "SELECT e FROM EuUtiliser e")
public class EuUtiliser implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idUtiliser;
    private Date dateCreation;
    private Long idCredit;
    private double montantAllouer;
    private EuSmcipnpwi euSmcipnpwi;
    private EuSmcipn smcipn;
    private EuSmc euSmc;

    public EuUtiliser() {
    }

    @Id
    @Column(name = "id_utiliser", unique = true, nullable = false)
    public Long getIdUtiliser() {
        return this.idUtiliser;
    }

    public void setIdUtiliser(Long idUtiliser) {
        this.idUtiliser = idUtiliser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_creation")
    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Column(name = "id_credit")
    public Long getIdCredit() {
        return this.idCredit;
    }

    public void setIdCredit(Long idCredit) {
        this.idCredit = idCredit;
    }

    @Column(name = "montant_allouer")
    public double getMontantAllouer() {
        return this.montantAllouer;
    }

    public void setMontantAllouer(double montantAllouer) {
        this.montantAllouer = montantAllouer;
    }

    //bi-directional many-to-one association to EuSmcipnpwi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_smcipn")
    public EuSmcipnpwi getEuSmcipnpwi() {
        return this.euSmcipnpwi;
    }

    public void setEuSmcipnpwi(EuSmcipnpwi euSmcipnpwi) {
        this.euSmcipnpwi = euSmcipnpwi;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_smcipn")
    public EuSmcipn getSmcipn() {
		return smcipn;
	}

	public void setSmcipn(EuSmcipn smcipn) {
		this.smcipn = smcipn;
	}

	//bi-directional many-to-one association to EuSmc
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_smc")
    public EuSmc getEuSmc() {
        return this.euSmc;
    }

    public void setEuSmc(EuSmc euSmc) {
        this.euSmc = euSmc;
    }

}
