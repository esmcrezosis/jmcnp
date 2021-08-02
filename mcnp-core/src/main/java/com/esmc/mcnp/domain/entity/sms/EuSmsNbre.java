package com.esmc.mcnp.model.sms;

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
 * The persistent class for the eu_sms_nbre database table.
 *
 */
@Entity
@Table(name = "eu_sms_nbre")
@NamedQuery(name = "EuSmsNbre.findAll", query = "SELECT e FROM EuSmsNbre e")
public class EuSmsNbre implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer smsNbreId;
    private Integer smsNbreAlerte;
    private Date smsNbreDate;
    private Integer smsNbreNbre;

    public EuSmsNbre() {
    }

    @Id
    @Column(name = "sms_nbre_id", unique = true, nullable = false)
    public Integer getSmsNbreId() {
        return this.smsNbreId;
    }

    public void setSmsNbreId(Integer smsNbreId) {
        this.smsNbreId = smsNbreId;
    }

    @Column(name = "sms_nbre_alerte")
    public Integer getSmsNbreAlerte() {
        return this.smsNbreAlerte;
    }

    public void setSmsNbreAlerte(Integer smsNbreAlerte) {
        this.smsNbreAlerte = smsNbreAlerte;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sms_nbre_date")
    public Date getSmsNbreDate() {
        return this.smsNbreDate;
    }

    public void setSmsNbreDate(Date smsNbreDate) {
        this.smsNbreDate = smsNbreDate;
    }

    @Column(name = "sms_nbre_nbre")
    public Integer getSmsNbreNbre() {
        return this.smsNbreNbre;
    }

    public void setSmsNbreNbre(Integer smsNbreNbre) {
        this.smsNbreNbre = smsNbreNbre;
    }

}
