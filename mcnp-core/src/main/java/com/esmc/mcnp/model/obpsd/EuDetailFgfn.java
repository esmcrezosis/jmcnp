package com.esmc.mcnp.model.obpsd;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;

/**
 * The persistent class for the eu_detail_fgfn database table.
 *
 */
@Entity
@Table(name = "eu_detail_fgfn")
@NamedQuery(name = "EuDetailFgfn.findAll", query = "SELECT e FROM EuDetailFgfn e")
public class EuDetailFgfn implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idFgfn;
    private String codeMembrePbf;
    private String creditcode;
    private Date dateFgfn;
    private double montFgfn;
    private double montPreleve;
    private String origineFgfn;
    private double soldeFgfn;
    private String typeFgfn;
    private EuFgfn euFgfn;
    private String codeBanque;

    public EuDetailFgfn() {
    }

    @Id
    @JsonView
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fgfn", unique = true, nullable = false)
    public Long getIdFgfn() {
        return this.idFgfn;
    }

    public void setIdFgfn(Long idFgfn) {
        this.idFgfn = idFgfn;
    }

    @JsonView
    @Column(name = "code_membre_pbf", length = 100)
    public String getCodeMembrePbf() {
        return this.codeMembrePbf;
    }

    public void setCodeMembrePbf(String codeMembrePbf) {
        this.codeMembrePbf = codeMembrePbf;
    }

    @JsonView
    @Column(length = 80)
    public String getCreditcode() {
        return this.creditcode;
    }

    public void setCreditcode(String creditcode) {
        this.creditcode = creditcode;
    }

    @JsonView
    @Temporal(TemporalType.DATE)
    @Column(name = "date_fgfn")
    public Date getDateFgfn() {
        return this.dateFgfn;
    }

    public void setDateFgfn(Date dateFgfn) {
        this.dateFgfn = dateFgfn;
    }

    @JsonView
    @Column(name = "mont_fgfn")
    public double getMontFgfn() {
        return this.montFgfn;
    }

    public void setMontFgfn(double montFgfn) {
        this.montFgfn = montFgfn;
    }

    @JsonView
    @Column(name = "mont_preleve")
    public double getMontPreleve() {
        return this.montPreleve;
    }

    public void setMontPreleve(double montPreleve) {
        this.montPreleve = montPreleve;
    }

    @JsonView
    @Column(name = "origine_fgfn", nullable = false, length = 20)
    public String getOrigineFgfn() {
        return this.origineFgfn;
    }

    public void setOrigineFgfn(String origineFgfn) {
        this.origineFgfn = origineFgfn;
    }

    @JsonView
    @Column(name = "solde_fgfn")
    public double getSoldeFgfn() {
        return this.soldeFgfn;
    }

    public void setSoldeFgfn(double soldeFgfn) {
        this.soldeFgfn = soldeFgfn;
    }

    @JsonView
    @Column(name = "type_fgfn", length = 25)
    public String getTypeFgfn() {
        return this.typeFgfn;
    }

    public void setTypeFgfn(String typeFgfn) {
        this.typeFgfn = typeFgfn;
    }

    // bi-directional many-to-one association to EuFgfn
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_fgfn")
    public EuFgfn getEuFgfn() {
        return this.euFgfn;
    }

    public void setEuFgfn(EuFgfn euFgfn) {
        this.euFgfn = euFgfn;
    }

    @JsonView
    @Column(name = "code_banque")
    public String getCodeBanque() {
        return codeBanque;
    }

    public void setCodeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
    }

}
