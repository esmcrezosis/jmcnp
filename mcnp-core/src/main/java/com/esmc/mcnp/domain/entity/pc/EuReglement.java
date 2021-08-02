package com.esmc.mcnp.model.pc;

import com.esmc.mcnp.model.others.EuFacture;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the eu_reglement database table.
 *
 */
@Entity
@Table(name = "eu_reglement")
@NamedQuery(name = "EuReglement.findAll", query = "SELECT e FROM EuReglement e")
public class EuReglement implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idReglt;
    private Date dateReglt;
    private double montantReglt;
    private EuFacture euFacture;

    public EuReglement() {
    }

    @Id
    @Column(name = "id_reglt", unique = true, nullable = false)
    public Long getIdReglt() {
        return this.idReglt;
    }

    public void setIdReglt(Long idReglt) {
        this.idReglt = idReglt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_reglt")
    public Date getDateReglt() {
        return this.dateReglt;
    }

    public void setDateReglt(Date dateReglt) {
        this.dateReglt = dateReglt;
    }

    @Column(name = "montant_reglt")
    public double getMontantReglt() {
        return this.montantReglt;
    }

    public void setMontantReglt(double montantReglt) {
        this.montantReglt = montantReglt;
    }

    @ManyToOne
    @JoinColumn(name = "code_facture")
    public EuFacture getEuFacture() {
        return euFacture;
    }

    public void setEuFacture(EuFacture euFacture) {
        this.euFacture = euFacture;
    }
}
