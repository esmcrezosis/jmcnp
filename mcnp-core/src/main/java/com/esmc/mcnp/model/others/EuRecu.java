package com.esmc.mcnp.model.others;

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
 * The persistent class for the eu_recu database table.
 *
 */
@Entity
@Table(name = "eu_recu")
@NamedQuery(name = "EuRecu.findAll", query = "SELECT e FROM EuRecu e")
public class EuRecu implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long recuId;
    private Integer publier;
    private Integer recuBps;
    private String recuCodeMembre;
    private Date recuDate;
    private Date recuDateDebut;
    private Date recuDateFin;
    private String recuFacture;
    private double recuMontant;
    private double recuMontantCredit;
    private String recuNumero;
    private Long recuUtilisateur;

    public EuRecu() {
    }

    @Id
    @Column(name = "recu_id", unique = true, nullable = false)
    public Long getRecuId() {
        return this.recuId;
    }

    public void setRecuId(Long recuId) {
        this.recuId = recuId;
    }

    public Integer getPublier() {
        return this.publier;
    }

    public void setPublier(Integer publier) {
        this.publier = publier;
    }

    @Column(name = "recu_bps")
    public Integer getRecuBps() {
        return this.recuBps;
    }

    public void setRecuBps(Integer recuBps) {
        this.recuBps = recuBps;
    }

    @Column(name = "recu_code_membre", length = 25)
    public String getRecuCodeMembre() {
        return this.recuCodeMembre;
    }

    public void setRecuCodeMembre(String recuCodeMembre) {
        this.recuCodeMembre = recuCodeMembre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "recu_date")
    public Date getRecuDate() {
        return this.recuDate;
    }

    public void setRecuDate(Date recuDate) {
        this.recuDate = recuDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "recu_date_debut")
    public Date getRecuDateDebut() {
        return this.recuDateDebut;
    }

    public void setRecuDateDebut(Date recuDateDebut) {
        this.recuDateDebut = recuDateDebut;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "recu_date_fin")
    public Date getRecuDateFin() {
        return this.recuDateFin;
    }

    public void setRecuDateFin(Date recuDateFin) {
        this.recuDateFin = recuDateFin;
    }

    @Column(name = "recu_facture", length = 50)
    public String getRecuFacture() {
        return this.recuFacture;
    }

    public void setRecuFacture(String recuFacture) {
        this.recuFacture = recuFacture;
    }

    @Column(name = "recu_montant")
    public double getRecuMontant() {
        return this.recuMontant;
    }

    public void setRecuMontant(double recuMontant) {
        this.recuMontant = recuMontant;
    }

    @Column(name = "recu_montant_credit")
    public double getRecuMontantCredit() {
        return this.recuMontantCredit;
    }

    public void setRecuMontantCredit(double recuMontantCredit) {
        this.recuMontantCredit = recuMontantCredit;
    }

    @Column(name = "recu_numero", length = 50)
    public String getRecuNumero() {
        return this.recuNumero;
    }

    public void setRecuNumero(String recuNumero) {
        this.recuNumero = recuNumero;
    }

    @Column(name = "recu_utilisateur")
    public Long getRecuUtilisateur() {
        return this.recuUtilisateur;
    }

    public void setRecuUtilisateur(Long recuUtilisateur) {
        this.recuUtilisateur = recuUtilisateur;
    }

}
