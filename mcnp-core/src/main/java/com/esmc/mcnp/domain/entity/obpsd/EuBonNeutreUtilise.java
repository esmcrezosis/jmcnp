package com.esmc.mcnp.domain.entity.obpsd;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the eu_bon_neutre_detail database table.
 */
@Entity
@Table(name = "eu_bon_neutre_utilise")
@NamedQuery(name = "EuBonNeutreUtilise.findAll", query = "SELECT e FROM EuBonNeutreUtilise e")
public class EuBonNeutreUtilise implements Serializable {
    private static final long serialVersionUID = 1L;
    private int bonNeutreUtiliseId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date bonNeutreUtiliseDate;
    private String bonNeutreUtiliseLibelle;
    private double bonNeutreUtiliseMontant;
    private String bonNeutreUtiliseType;
    private Integer bonNeutreDetailId;
    private EuBonNeutre euBonNeutre;
    private EuBonNeutreDetail bonNeutreDetail;

    public EuBonNeutreUtilise() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bon_neutre_utilise_id", unique = true, nullable = false)
    public int getBonNeutreUtiliseId() {
        return this.bonNeutreUtiliseId;
    }

    public void setBonNeutreUtiliseId(int bonNeutreUtiliseId) {
        this.bonNeutreUtiliseId = bonNeutreUtiliseId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bon_neutre_utilise_date")
    public Date getBonNeutreUtiliseDate() {
        return this.bonNeutreUtiliseDate;
    }

    public void setBonNeutreUtiliseDate(Date bonNeutreUtiliseDate) {
        this.bonNeutreUtiliseDate = bonNeutreUtiliseDate;
    }

    @Column(name = "bon_neutre_utilise_libelle", length = 100)
    public String getBonNeutreUtiliseLibelle() {
        return this.bonNeutreUtiliseLibelle;
    }

    public void setBonNeutreUtiliseLibelle(String bonNeutreUtiliseLibelle) {
        this.bonNeutreUtiliseLibelle = bonNeutreUtiliseLibelle;
    }

    @Column(name = "bon_neutre_utilise_montant")
    public double getBonNeutreUtiliseMontant() {
        return this.bonNeutreUtiliseMontant;
    }

    public void setBonNeutreUtiliseMontant(double bonNeutreUtiliseMontant) {
        this.bonNeutreUtiliseMontant = bonNeutreUtiliseMontant;
    }

    @Column(name = "bon_neutre_utilise_type", length = 25)
    public String getBonNeutreUtiliseType() {
        return this.bonNeutreUtiliseType;
    }

    public void setBonNeutreUtiliseType(String bonNeutreUtiliseType) {
        this.bonNeutreUtiliseType = bonNeutreUtiliseType;
    }

    @Column(name = "bon_neutre_detail_id")
    public Integer getBonNeutreDetailId() {
        return bonNeutreDetailId;
    }

    public void setBonNeutreDetailId(Integer bonNeutreDetailId) {
        this.bonNeutreDetailId = bonNeutreDetailId;
    }

    // bi-directional many-to-one association to EuBonNeutre
    @ManyToOne
    @JoinColumn(name = "bon_neutre_id")
    public EuBonNeutre getEuBonNeutre() {
        return this.euBonNeutre;
    }

    public void setEuBonNeutre(EuBonNeutre euBonNeutre) {
        this.euBonNeutre = euBonNeutre;
    }

    @ManyToOne
    @JoinColumn(name = "bon_neutre_detail_id", insertable = false, updatable = false)
    public EuBonNeutreDetail getBonNeutreDetail() {
        return bonNeutreDetail;
    }

    public void setBonNeutreDetail(EuBonNeutreDetail bonNeutreDetail) {
        this.bonNeutreDetail = bonNeutreDetail;
    }
}