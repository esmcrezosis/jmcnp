package com.esmc.mcnp.model.cm;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_ancien_compte database table.
 *
 */
@Entity
@Table(name = "eu_ancien_compte")
@NamedQuery(name = "EuAncienCompte.findAll", query = "SELECT e FROM EuAncienCompte e")
public class EuAncienCompte implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeCompte;
    private String cardprinteddate;
    private Integer cardprintediddate;
    private String codeCat;
    private String codeMembre;
    private Date dateAlloc;
    private Integer desactiver;
    private String libCompte;
    private String mifarecard;
    private double solde;
    private EuTypeCompte euTypeCompte;

    public EuAncienCompte() {
    }

    @Id
    @Column(name = "code_compte", unique = true, nullable = false, length = 100)
    public String getCodeCompte() {
        return this.codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    @Column(length = 60)
    public String getCardprinteddate() {
        return this.cardprinteddate;
    }

    public void setCardprinteddate(String cardprinteddate) {
        this.cardprinteddate = cardprinteddate;
    }

    public Integer getCardprintediddate() {
        return this.cardprintediddate;
    }

    public void setCardprintediddate(Integer cardprintediddate) {
        this.cardprintediddate = cardprintediddate;
    }

    @Column(name = "code_cat", length = 60)
    public String getCodeCat() {
        return this.codeCat;
    }

    public void setCodeCat(String codeCat) {
        this.codeCat = codeCat;
    }

    @Column(name = "code_membre", length = 100)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_alloc")
    public Date getDateAlloc() {
        return this.dateAlloc;
    }

    public void setDateAlloc(Date dateAlloc) {
        this.dateAlloc = dateAlloc;
    }

    @Column(nullable = false)
    public Integer getDesactiver() {
        return this.desactiver;
    }

    public void setDesactiver(Integer desactiver) {
        this.desactiver = desactiver;
    }

    @Column(name = "lib_compte", nullable = false, length = 200)
    public String getLibCompte() {
        return this.libCompte;
    }

    public void setLibCompte(String libCompte) {
        this.libCompte = libCompte;
    }

    @Column(length = 300)
    public String getMifarecard() {
        return this.mifarecard;
    }

    public void setMifarecard(String mifarecard) {
        this.mifarecard = mifarecard;
    }

    public double getSolde() {
        return this.solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    //bi-directional many-to-one association to EuTypeCompte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_type_compte")
    public EuTypeCompte getEuTypeCompte() {
        return this.euTypeCompte;
    }

    public void setEuTypeCompte(EuTypeCompte euTypeCompte) {
        this.euTypeCompte = euTypeCompte;
    }

}
