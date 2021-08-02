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
 * The persistent class for the eu_offre_demande_cloture database table.
 *
 */
@Entity
@Table(name = "eu_offre_demande_cloture")
@NamedQuery(name = "EuOffreDemandeCloture.findAll", query = "SELECT e FROM EuOffreDemandeCloture e")
public class EuOffreDemandeCloture implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idCloture;
    private Integer cloture;
    private String clotureMembre;
    private String codeCompteDemande;
    private String codeCompteOffre;
    private String codeMembreDemande;
    private String codeMembreOffre;
    private String codeSmsDemande;
    private String codeSmsOffre;
    private Date dateCloture;
    private Long idCreditDemande;
    private Long idCreditOffre;
    private Long idDemande;
    private Long idOffre;
    private double montantDemande;
    private double montantOffre;
    private String numOffreDemande;

    public EuOffreDemandeCloture() {
    }

    @Id
    @Column(name = "id_cloture", unique = true, nullable = false)
    public Long getIdCloture() {
        return this.idCloture;
    }

    public void setIdCloture(Long idCloture) {
        this.idCloture = idCloture;
    }

    public Integer getCloture() {
        return this.cloture;
    }

    public void setCloture(Integer cloture) {
        this.cloture = cloture;
    }

    @Column(name = "cloture_membre", length = 125)
    public String getClotureMembre() {
        return this.clotureMembre;
    }

    public void setClotureMembre(String clotureMembre) {
        this.clotureMembre = clotureMembre;
    }

    @Column(name = "code_compte_demande", length = 125)
    public String getCodeCompteDemande() {
        return this.codeCompteDemande;
    }

    public void setCodeCompteDemande(String codeCompteDemande) {
        this.codeCompteDemande = codeCompteDemande;
    }

    @Column(name = "code_compte_offre", length = 125)
    public String getCodeCompteOffre() {
        return this.codeCompteOffre;
    }

    public void setCodeCompteOffre(String codeCompteOffre) {
        this.codeCompteOffre = codeCompteOffre;
    }

    @Column(name = "code_membre_demande", length = 125)
    public String getCodeMembreDemande() {
        return this.codeMembreDemande;
    }

    public void setCodeMembreDemande(String codeMembreDemande) {
        this.codeMembreDemande = codeMembreDemande;
    }

    @Column(name = "code_membre_offre", length = 125)
    public String getCodeMembreOffre() {
        return this.codeMembreOffre;
    }

    public void setCodeMembreOffre(String codeMembreOffre) {
        this.codeMembreOffre = codeMembreOffre;
    }

    @Column(name = "code_sms_demande", length = 9)
    public String getCodeSmsDemande() {
        return this.codeSmsDemande;
    }

    public void setCodeSmsDemande(String codeSmsDemande) {
        this.codeSmsDemande = codeSmsDemande;
    }

    @Column(name = "code_sms_offre", length = 9)
    public String getCodeSmsOffre() {
        return this.codeSmsOffre;
    }

    public void setCodeSmsOffre(String codeSmsOffre) {
        this.codeSmsOffre = codeSmsOffre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_cloture")
    public Date getDateCloture() {
        return this.dateCloture;
    }

    public void setDateCloture(Date dateCloture) {
        this.dateCloture = dateCloture;
    }

    @Column(name = "id_credit_demande", length = 45)
    public Long getIdCreditDemande() {
        return this.idCreditDemande;
    }

    public void setIdCreditDemande(Long idCreditDemande) {
        this.idCreditDemande = idCreditDemande;
    }

    @Column(name = "id_credit_offre", length = 45)
    public Long getIdCreditOffre() {
        return this.idCreditOffre;
    }

    public void setIdCreditOffre(Long idCreditOffre) {
        this.idCreditOffre = idCreditOffre;
    }

    @Column(name = "id_demande")
    public Long getIdDemande() {
        return this.idDemande;
    }

    public void setIdDemande(Long idDemande) {
        this.idDemande = idDemande;
    }

    @Column(name = "id_offre")
    public Long getIdOffre() {
        return this.idOffre;
    }

    public void setIdOffre(Long idOffre) {
        this.idOffre = idOffre;
    }

    @Column(name = "montant_demande")
    public double getMontantDemande() {
        return this.montantDemande;
    }

    public void setMontantDemande(double montantDemande) {
        this.montantDemande = montantDemande;
    }

    @Column(name = "montant_offre")
    public double getMontantOffre() {
        return this.montantOffre;
    }

    public void setMontantOffre(double montantOffre) {
        this.montantOffre = montantOffre;
    }

    @Column(name = "num_offre_demande", length = 255)
    public String getNumOffreDemande() {
        return this.numOffreDemande;
    }

    public void setNumOffreDemande(String numOffreDemande) {
        this.numOffreDemande = numOffreDemande;
    }

}
