package com.esmc.mcnp.model.others;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "eu_validation_quittance")
public class EuValidationQuittance {
    private int validationQuittanceId;
    private Timestamp validationQuittanceDate;
    private Integer validationQuittanceUtilisateur;
    private Integer validationQuittanceSouscription;
    private Integer publier;
    private Integer validationQuittanceAcheteur;
    private Integer validationQuittanceLivraison;
    private Integer validationQuittancePreinscription;
    private Integer validationQuittancePreinscriptionMorale;
    private Integer validationBc;

    @Id
    @Column(name = "validation_quittance_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getValidationQuittanceId() {
        return validationQuittanceId;
    }

    public void setValidationQuittanceId(int validationQuittanceId) {
        this.validationQuittanceId = validationQuittanceId;
    }

    @Basic
    @Column(name = "validation_quittance_date")
    public Timestamp getValidationQuittanceDate() {
        return validationQuittanceDate;
    }

    public void setValidationQuittanceDate(Timestamp validationQuittanceDate) {
        this.validationQuittanceDate = validationQuittanceDate;
    }

    @Basic
    @Column(name = "validation_quittance_utilisateur")
    public Integer getValidationQuittanceUtilisateur() {
        return validationQuittanceUtilisateur;
    }

    public void setValidationQuittanceUtilisateur(Integer validationQuittanceUtilisateur) {
        this.validationQuittanceUtilisateur = validationQuittanceUtilisateur;
    }

    @Basic
    @Column(name = "validation_quittance_souscription")
    public Integer getValidationQuittanceSouscription() {
        return validationQuittanceSouscription;
    }

    public void setValidationQuittanceSouscription(Integer validationQuittanceSouscription) {
        this.validationQuittanceSouscription = validationQuittanceSouscription;
    }

    @Basic
    @Column(name = "publier")
    public Integer getPublier() {
        return publier;
    }

    public void setPublier(Integer publier) {
        this.publier = publier;
    }

    @Basic
    @Column(name = "validation_quittance_acheteur")
    public Integer getValidationQuittanceAcheteur() {
        return validationQuittanceAcheteur;
    }

    public void setValidationQuittanceAcheteur(Integer validationQuittanceAcheteur) {
        this.validationQuittanceAcheteur = validationQuittanceAcheteur;
    }

    @Basic
    @Column(name = "validation_quittance_livraison")
    public Integer getValidationQuittanceLivraison() {
        return validationQuittanceLivraison;
    }

    public void setValidationQuittanceLivraison(Integer validationQuittanceLivraison) {
        this.validationQuittanceLivraison = validationQuittanceLivraison;
    }

    @Basic
    @Column(name = "validation_quittance_preinscription")
    public Integer getValidationQuittancePreinscription() {
        return validationQuittancePreinscription;
    }

    public void setValidationQuittancePreinscription(Integer validationQuittancePreinscription) {
        this.validationQuittancePreinscription = validationQuittancePreinscription;
    }

    @Basic
    @Column(name = "validation_quittance_preinscription_morale")
    public Integer getValidationQuittancePreinscriptionMorale() {
        return validationQuittancePreinscriptionMorale;
    }

    public void setValidationQuittancePreinscriptionMorale(Integer validationQuittancePreinscriptionMorale) {
        this.validationQuittancePreinscriptionMorale = validationQuittancePreinscriptionMorale;
    }

    @Basic
    @Column(name = "validation_bc")
    public Integer getValidationBc() {
        return validationBc;
    }

    public void setValidationBc(Integer validationBc) {
        this.validationBc = validationBc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EuValidationQuittance that = (EuValidationQuittance) o;
        return validationQuittanceId == that.validationQuittanceId &&
                Objects.equals(validationQuittanceDate, that.validationQuittanceDate) &&
                Objects.equals(validationQuittanceUtilisateur, that.validationQuittanceUtilisateur) &&
                Objects.equals(validationQuittanceSouscription, that.validationQuittanceSouscription) &&
                Objects.equals(publier, that.publier) &&
                Objects.equals(validationQuittanceAcheteur, that.validationQuittanceAcheteur) &&
                Objects.equals(validationQuittanceLivraison, that.validationQuittanceLivraison) &&
                Objects.equals(validationQuittancePreinscription, that.validationQuittancePreinscription) &&
                Objects.equals(validationQuittancePreinscriptionMorale, that.validationQuittancePreinscriptionMorale) &&
                Objects.equals(validationBc, that.validationBc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validationQuittanceId, validationQuittanceDate, validationQuittanceUtilisateur, validationQuittanceSouscription, publier, validationQuittanceAcheteur, validationQuittanceLivraison, validationQuittancePreinscription, validationQuittancePreinscriptionMorale, validationBc);
    }
}
