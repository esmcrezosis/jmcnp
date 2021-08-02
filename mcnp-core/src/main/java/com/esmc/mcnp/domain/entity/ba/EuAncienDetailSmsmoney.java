package com.esmc.mcnp.domain.entity.ba;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_ancien_detail_smsmoney database table.
 *
 */
@Entity
@Table(name = "eu_ancien_detail_smsmoney")
@NamedQuery(name = "EuAncienDetailSmsmoney.findAll", query = "SELECT e FROM EuAncienDetailSmsmoney e")
public class EuAncienDetailSmsmoney implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idDetailSmsmoney;
    private String codeMembre;
    private String codeMembreDist;
    private String creditcode;
    private Date dateAllocation;
    private Long idUtilisateur;
    private double montRegle;
    private double montSms;
    private double montVendu;
    private Integer numBon;
    private String origineSms;
    private double soldeSms;
    private String typeSms;

    public EuAncienDetailSmsmoney() {
    }

    @Id
    @Column(name = "id_detail_smsmoney", unique = true, nullable = false)
    public Long getIdDetailSmsmoney() {
        return this.idDetailSmsmoney;
    }

    public void setIdDetailSmsmoney(Long idDetailSmsmoney) {
        this.idDetailSmsmoney = idDetailSmsmoney;
    }

    @Column(name = "code_membre", length = 200)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(name = "code_membre_dist", length = 200)
    public String getCodeMembreDist() {
        return this.codeMembreDist;
    }

    public void setCodeMembreDist(String codeMembreDist) {
        this.codeMembreDist = codeMembreDist;
    }

    @Column(length = 40)
    public String getCreditcode() {
        return this.creditcode;
    }

    public void setCreditcode(String creditcode) {
        this.creditcode = creditcode;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_allocation")
    public Date getDateAllocation() {
        return this.dateAllocation;
    }

    public void setDateAllocation(Date dateAllocation) {
        this.dateAllocation = dateAllocation;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "mont_regle", nullable = false)
    public double getMontRegle() {
        return this.montRegle;
    }

    public void setMontRegle(double montRegle) {
        this.montRegle = montRegle;
    }

    @Column(name = "mont_sms")
    public double getMontSms() {
        return this.montSms;
    }

    public void setMontSms(double montSms) {
        this.montSms = montSms;
    }

    @Column(name = "mont_vendu")
    public double getMontVendu() {
        return this.montVendu;
    }

    public void setMontVendu(double montVendu) {
        this.montVendu = montVendu;
    }

    @Column(name = "num_bon")
    public Integer getNumBon() {
        return this.numBon;
    }

    public void setNumBon(Integer numBon) {
        this.numBon = numBon;
    }

    @Column(name = "origine_sms", length = 20)
    public String getOrigineSms() {
        return this.origineSms;
    }

    public void setOrigineSms(String origineSms) {
        this.origineSms = origineSms;
    }

    @Column(name = "solde_sms")
    public double getSoldeSms() {
        return this.soldeSms;
    }

    public void setSoldeSms(double soldeSms) {
        this.soldeSms = soldeSms;
    }

    @Column(name = "type_sms", length = 60)
    public String getTypeSms() {
        return this.typeSms;
    }

    public void setTypeSms(String typeSms) {
        this.typeSms = typeSms;
    }

}
