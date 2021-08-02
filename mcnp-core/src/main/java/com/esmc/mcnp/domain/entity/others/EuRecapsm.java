package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_recapsms database table.
 *
 */
@Entity
@Table(name = "eu_recapsms")
@NamedQuery(name = "EuRecapsm.findAll", query = "SELECT e FROM EuRecapsm e")
public class EuRecapsm implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String banque;
    private double creditamount;
    private String creditcode;
    private String datetime;
    private String motif;
    private String sentto;

    public EuRecapsm() {
    }

    @Id
    @Column(unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 200)
    public String getBanque() {
        return this.banque;
    }

    public void setBanque(String banque) {
        this.banque = banque;
    }

    public double getCreditamount() {
        return this.creditamount;
    }

    public void setCreditamount(double creditamount) {
        this.creditamount = creditamount;
    }

    @Column(length = 80)
    public String getCreditcode() {
        return this.creditcode;
    }

    public void setCreditcode(String creditcode) {
        this.creditcode = creditcode;
    }

    @Column(length = 80)
    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Column(length = 80)
    public String getMotif() {
        return this.motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    @Column(length = 80)
    public String getSentto() {
        return this.sentto;
    }

    public void setSentto(String sentto) {
        this.sentto = sentto;
    }

}
