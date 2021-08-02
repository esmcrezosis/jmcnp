package com.esmc.mcnp.domain.entity.obpsd;

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

import com.esmc.mcnp.domain.entity.cm.EuCompte;

/**
 * The persistent class for the eu_transfert_nn database table.
 *
 */
@Entity
@Table(name = "eu_transfert_nn")
@NamedQuery(name = "EuTransfertNn.findAll", query = "SELECT e FROM EuTransfertNn e")
public class EuTransfertNn implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTransfertNn;
    private String codeDocument;
    private Date dateTransfert;
    private double montRegle;
    private double montTransfert;
    private double montVendu;
    private double restantDu;
    private double soldeTransfert;
    private String typeReglement;
    private String typeTransfert;
    private String urlDocument;
    private EuCompte euCompteDist;
    private EuCompte euCompteTransfert;
    private EuTypeNn euTypeNn;

    public EuTransfertNn() {
    }

    @Id
    @Column(name = "id_transfert_nn", unique = true, nullable = false)
    public Long getIdTransfertNn() {
        return this.idTransfertNn;
    }

    public void setIdTransfertNn(Long idTransfertNn) {
        this.idTransfertNn = idTransfertNn;
    }

    @Column(name = "code_document", length = 150)
    public String getCodeDocument() {
        return this.codeDocument;
    }

    public void setCodeDocument(String codeDocument) {
        this.codeDocument = codeDocument;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_transfert")
    public Date getDateTransfert() {
        return this.dateTransfert;
    }

    public void setDateTransfert(Date dateTransfert) {
        this.dateTransfert = dateTransfert;
    }

    @Column(name = "mont_regle")
    public double getMontRegle() {
        return this.montRegle;
    }

    public void setMontRegle(double montRegle) {
        this.montRegle = montRegle;
    }

    @Column(name = "mont_transfert")
    public double getMontTransfert() {
        return this.montTransfert;
    }

    public void setMontTransfert(double montTransfert) {
        this.montTransfert = montTransfert;
    }

    @Column(name = "mont_vendu")
    public double getMontVendu() {
        return this.montVendu;
    }

    public void setMontVendu(double montVendu) {
        this.montVendu = montVendu;
    }

    @Column(name = "restant_du")
    public double getRestantDu() {
        return this.restantDu;
    }

    public void setRestantDu(double restantDu) {
        this.restantDu = restantDu;
    }

    @Column(name = "solde_transfert")
    public double getSoldeTransfert() {
        return this.soldeTransfert;
    }

    public void setSoldeTransfert(double soldeTransfert) {
        this.soldeTransfert = soldeTransfert;
    }

    @Column(name = "type_reglement", length = 255)
    public String getTypeReglement() {
        return this.typeReglement;
    }

    public void setTypeReglement(String typeReglement) {
        this.typeReglement = typeReglement;
    }

    @Column(name = "type_transfert", length = 255)
    public String getTypeTransfert() {
        return this.typeTransfert;
    }

    public void setTypeTransfert(String typeTransfert) {
        this.typeTransfert = typeTransfert;
    }

    @Column(name = "url_document", length = 200)
    public String getUrlDocument() {
        return this.urlDocument;
    }

    public void setUrlDocument(String urlDocument) {
        this.urlDocument = urlDocument;
    }

    //bi-directional many-to-one association to EuCompte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_compte_dist")
    public EuCompte getEuCompteDist() {
        return this.euCompteDist;
    }

    public void setEuCompteDist(EuCompte euCompteDist) {
        this.euCompteDist = euCompteDist;
    }

    //bi-directional many-to-one association to EuCompte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_compte_transfert")
    public EuCompte getEuCompteTransfert() {
        return this.euCompteTransfert;
    }

    public void setEuCompteTransfert(EuCompte euCompteTransfert) {
        this.euCompteTransfert = euCompteTransfert;
    }

    //bi-directional many-to-one association to EuTypeNn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_type_nn")
    public EuTypeNn getEuTypeNn() {
        return this.euTypeNn;
    }

    public void setEuTypeNn(EuTypeNn euTypeNn) {
        this.euTypeNn = euTypeNn;
    }

}
