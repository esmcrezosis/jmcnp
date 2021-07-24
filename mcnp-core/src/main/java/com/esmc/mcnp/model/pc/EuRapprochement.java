package com.esmc.mcnp.model.pc;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.esmc.mcnp.model.bc.EuDomiciliation;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.smcipn.EuSmcipn;
import com.esmc.mcnp.model.smcipn.EuSmcipnp;

/**
 * The persistent class for the eu_rapprochement database table.
 *
 */
@Entity
@Table(name = "eu_rapprochement")
@NamedQuery(name = "EuRapprochement.findAll", query = "SELECT e FROM EuRapprochement e")
public class EuRapprochement implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idRappro;
    private double creditRappro;
    private double debitRappro;
    private double soldeRappro;
    private String source;
    private String sourceCredit;
    private String typeRappro;
    private EuCompteCredit euCompteCredit;
    private EuDomiciliation euDomiciliation;
    private EuSmcipnp euSmcipnp;
    private EuSmcipn euSmcipn;

    public EuRapprochement() {
    }

    @Id
    @Column(name = "id_rappro", unique = true, nullable = false)
    public Long getIdRappro() {
        return this.idRappro;
    }

    public void setIdRappro(Long idRappro) {
        this.idRappro = idRappro;
    }

    @Column(name = "credit_rappro")
    public double getCreditRappro() {
        return this.creditRappro;
    }

    public void setCreditRappro(double creditRappro) {
        this.creditRappro = creditRappro;
    }

    @Column(name = "debit_rappro")
    public double getDebitRappro() {
        return this.debitRappro;
    }

    public void setDebitRappro(double debitRappro) {
        this.debitRappro = debitRappro;
    }

    @Column(name = "solde_rappro")
    public double getSoldeRappro() {
        return this.soldeRappro;
    }

    public void setSoldeRappro(double soldeRappro) {
        this.soldeRappro = soldeRappro;
    }

    @Column(length = 200)
    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "source_credit", length = 200)
    public String getSourceCredit() {
        return this.sourceCredit;
    }

    public void setSourceCredit(String sourceCredit) {
        this.sourceCredit = sourceCredit;
    }

    @Column(name = "type_rappro", length = 180)
    public String getTypeRappro() {
        return this.typeRappro;
    }

    public void setTypeRappro(String typeRappro) {
        this.typeRappro = typeRappro;
    }

    //bi-directional many-to-one association to EuCompteCredit
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_credit")
    public EuCompteCredit getEuCompteCredit() {
        return this.euCompteCredit;
    }

    public void setEuCompteCredit(EuCompteCredit euCompteCredit) {
        this.euCompteCredit = euCompteCredit;
    }

    //bi-directional many-to-one association to EuDomiciliation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_domicilier")
    public EuDomiciliation getEuDomiciliation() {
        return this.euDomiciliation;
    }

    public void setEuDomiciliation(EuDomiciliation euDomiciliation) {
        this.euDomiciliation = euDomiciliation;
    }

    //bi-directional many-to-one association to EuSmcipnp
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_smcipnp")
    public EuSmcipnp getEuSmcipnp() {
        return this.euSmcipnp;
    }

    public void setEuSmcipnp(EuSmcipnp euSmcipnp) {
        this.euSmcipnp = euSmcipnp;
    }

    //bi-directional many-to-one association to EuSmcipn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_smcipn")
    public EuSmcipn getEuSmcipn() {
        return this.euSmcipn;
    }

    public void setEuSmcipn(EuSmcipn euSmcipn) {
        this.euSmcipn = euSmcipn;
    }

}
