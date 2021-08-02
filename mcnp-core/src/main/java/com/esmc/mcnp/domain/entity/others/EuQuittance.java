package com.esmc.mcnp.domain.entity.others;

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
 * The persistent class for the eu_quittance database table.
 *
 */
@Entity
@Table(name = "eu_quittance")
@NamedQuery(name = "EuQuittance.findAll", query = "SELECT e FROM EuQuittance e")
public class EuQuittance implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer quittanceId;
    private Integer publier;
    private String quittanceBanque;
    private double quittanceCandidat;
    private String quittanceCel;
    private String quittanceCode;
    private String quittanceCodeMembre;
    private Date quittanceDate;
    private String quittanceNom;
    private String quittanceNumero;
    private Integer quittanceType;

    public EuQuittance() {
    }

    @Id
    @Column(name = "quittance_id", unique = true, nullable = false)
    public Integer getQuittanceId() {
        return this.quittanceId;
    }

    public void setQuittanceId(Integer quittanceId) {
        this.quittanceId = quittanceId;
    }

    public Integer getPublier() {
        return this.publier;
    }

    public void setPublier(Integer publier) {
        this.publier = publier;
    }

    @Column(name = "quittance_banque", length = 10)
    public String getQuittanceBanque() {
        return this.quittanceBanque;
    }

    public void setQuittanceBanque(String quittanceBanque) {
        this.quittanceBanque = quittanceBanque;
    }

    @Column(name = "quittance_candidat")
    public double getQuittanceCandidat() {
        return this.quittanceCandidat;
    }

    public void setQuittanceCandidat(double quittanceCandidat) {
        this.quittanceCandidat = quittanceCandidat;
    }

    @Column(name = "quittance_cel", length = 20)
    public String getQuittanceCel() {
        return this.quittanceCel;
    }

    public void setQuittanceCel(String quittanceCel) {
        this.quittanceCel = quittanceCel;
    }

    @Column(name = "quittance_code", length = 255)
    public String getQuittanceCode() {
        return this.quittanceCode;
    }

    public void setQuittanceCode(String quittanceCode) {
        this.quittanceCode = quittanceCode;
    }

    @Column(name = "quittance_code_membre", length = 25)
    public String getQuittanceCodeMembre() {
        return this.quittanceCodeMembre;
    }

    public void setQuittanceCodeMembre(String quittanceCodeMembre) {
        this.quittanceCodeMembre = quittanceCodeMembre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "quittance_date")
    public Date getQuittanceDate() {
        return this.quittanceDate;
    }

    public void setQuittanceDate(Date quittanceDate) {
        this.quittanceDate = quittanceDate;
    }

    @Column(name = "quittance_nom", length = 255)
    public String getQuittanceNom() {
        return this.quittanceNom;
    }

    public void setQuittanceNom(String quittanceNom) {
        this.quittanceNom = quittanceNom;
    }

    @Column(name = "quittance_numero", length = 255)
    public String getQuittanceNumero() {
        return this.quittanceNumero;
    }

    public void setQuittanceNumero(String quittanceNumero) {
        this.quittanceNumero = quittanceNumero;
    }

    @Column(name = "quittance_type")
    public Integer getQuittanceType() {
        return this.quittanceType;
    }

    public void setQuittanceType(Integer quittanceType) {
        this.quittanceType = quittanceType;
    }

}
