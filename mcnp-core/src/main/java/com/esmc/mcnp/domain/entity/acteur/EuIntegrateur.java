package com.esmc.mcnp.domain.entity.acteur;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "eu_integrateur")
public class EuIntegrateur {
    private int integrateurId;
    private Integer integrateurSouscription;
    private Integer integrateurCritere1;
    private Integer integrateurCritere2;
    private Integer integrateurCritere3;
    private Integer integrateurType;
    private Timestamp integrateurDate;
    private Integer publier;
    private String integrateurPoste;
    private String integrateurDocument;
    private String integrateurEducation;
    private String integrateurAffiliation;
    private String integrateurFormation;
    private String integrateurLangue;
    private String integrateurExperience;
    private Double integrateurAttestation;
    private String integrateurDiplome;
    private Integer integrateurMembreasso;
    private String integrateurCanton;
    private String integrateurVille;
    private String integrateurAdresse;
    private String codeMembre;

    @Id
    @Column(name = "integrateur_id")
    public int getIntegrateurId() {
        return integrateurId;
    }

    public void setIntegrateurId(int integrateurId) {
        this.integrateurId = integrateurId;
    }

    @Basic
    @Column(name = "integrateur_souscription")
    public Integer getIntegrateurSouscription() {
        return integrateurSouscription;
    }

    public void setIntegrateurSouscription(Integer integrateurSouscription) {
        this.integrateurSouscription = integrateurSouscription;
    }

    @Basic
    @Column(name = "integrateur_critere1")
    public Integer getIntegrateurCritere1() {
        return integrateurCritere1;
    }

    public void setIntegrateurCritere1(Integer integrateurCritere1) {
        this.integrateurCritere1 = integrateurCritere1;
    }

    @Basic
    @Column(name = "integrateur_critere2")
    public Integer getIntegrateurCritere2() {
        return integrateurCritere2;
    }

    public void setIntegrateurCritere2(Integer integrateurCritere2) {
        this.integrateurCritere2 = integrateurCritere2;
    }

    @Basic
    @Column(name = "integrateur_critere3")
    public Integer getIntegrateurCritere3() {
        return integrateurCritere3;
    }

    public void setIntegrateurCritere3(Integer integrateurCritere3) {
        this.integrateurCritere3 = integrateurCritere3;
    }

    @Basic
    @Column(name = "integrateur_type")
    public Integer getIntegrateurType() {
        return integrateurType;
    }

    public void setIntegrateurType(Integer integrateurType) {
        this.integrateurType = integrateurType;
    }

    @Basic
    @Column(name = "integrateur_date")
    public Timestamp getIntegrateurDate() {
        return integrateurDate;
    }

    public void setIntegrateurDate(Timestamp integrateurDate) {
        this.integrateurDate = integrateurDate;
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
    @Column(name = "integrateur_poste")
    public String getIntegrateurPoste() {
        return integrateurPoste;
    }

    public void setIntegrateurPoste(String integrateurPoste) {
        this.integrateurPoste = integrateurPoste;
    }

    @Basic
    @Column(name = "integrateur_document")
    public String getIntegrateurDocument() {
        return integrateurDocument;
    }

    public void setIntegrateurDocument(String integrateurDocument) {
        this.integrateurDocument = integrateurDocument;
    }

    @Basic
    @Column(name = "integrateur_education")
    public String getIntegrateurEducation() {
        return integrateurEducation;
    }

    public void setIntegrateurEducation(String integrateurEducation) {
        this.integrateurEducation = integrateurEducation;
    }

    @Basic
    @Column(name = "integrateur_affiliation")
    public String getIntegrateurAffiliation() {
        return integrateurAffiliation;
    }

    public void setIntegrateurAffiliation(String integrateurAffiliation) {
        this.integrateurAffiliation = integrateurAffiliation;
    }

    @Basic
    @Column(name = "integrateur_formation")
    public String getIntegrateurFormation() {
        return integrateurFormation;
    }

    public void setIntegrateurFormation(String integrateurFormation) {
        this.integrateurFormation = integrateurFormation;
    }

    @Basic
    @Column(name = "integrateur_langue")
    public String getIntegrateurLangue() {
        return integrateurLangue;
    }

    public void setIntegrateurLangue(String integrateurLangue) {
        this.integrateurLangue = integrateurLangue;
    }

    @Basic
    @Column(name = "integrateur_experience")
    public String getIntegrateurExperience() {
        return integrateurExperience;
    }

    public void setIntegrateurExperience(String integrateurExperience) {
        this.integrateurExperience = integrateurExperience;
    }

    @Basic
    @Column(name = "integrateur_attestation")
    public Double getIntegrateurAttestation() {
        return integrateurAttestation;
    }

    public void setIntegrateurAttestation(Double integrateurAttestation) {
        this.integrateurAttestation = integrateurAttestation;
    }

    @Basic
    @Column(name = "integrateur_diplome")
    public String getIntegrateurDiplome() {
        return integrateurDiplome;
    }

    public void setIntegrateurDiplome(String integrateurDiplome) {
        this.integrateurDiplome = integrateurDiplome;
    }

    @Basic
    @Column(name = "integrateur_membreasso")
    public Integer getIntegrateurMembreasso() {
        return integrateurMembreasso;
    }

    public void setIntegrateurMembreasso(Integer integrateurMembreasso) {
        this.integrateurMembreasso = integrateurMembreasso;
    }

    @Basic
    @Column(name = "integrateur_canton")
    public String getIntegrateurCanton() {
        return integrateurCanton;
    }

    public void setIntegrateurCanton(String integrateurCanton) {
        this.integrateurCanton = integrateurCanton;
    }

    @Basic
    @Column(name = "integrateur_ville")
    public String getIntegrateurVille() {
        return integrateurVille;
    }

    public void setIntegrateurVille(String integrateurVille) {
        this.integrateurVille = integrateurVille;
    }

    @Basic
    @Column(name = "integrateur_adresse")
    public String getIntegrateurAdresse() {
        return integrateurAdresse;
    }

    public void setIntegrateurAdresse(String integrateurAdresse) {
        this.integrateurAdresse = integrateurAdresse;
    }

    @Basic
    @Column(name = "code_membre")
    public String getCodeMembre() {
        return codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EuIntegrateur that = (EuIntegrateur) o;
        return integrateurId == that.integrateurId &&
                Objects.equals(integrateurSouscription, that.integrateurSouscription) &&
                Objects.equals(integrateurCritere1, that.integrateurCritere1) &&
                Objects.equals(integrateurCritere2, that.integrateurCritere2) &&
                Objects.equals(integrateurCritere3, that.integrateurCritere3) &&
                Objects.equals(integrateurType, that.integrateurType) &&
                Objects.equals(integrateurDate, that.integrateurDate) &&
                Objects.equals(publier, that.publier) &&
                Objects.equals(integrateurPoste, that.integrateurPoste) &&
                Objects.equals(integrateurDocument, that.integrateurDocument) &&
                Objects.equals(integrateurEducation, that.integrateurEducation) &&
                Objects.equals(integrateurAffiliation, that.integrateurAffiliation) &&
                Objects.equals(integrateurFormation, that.integrateurFormation) &&
                Objects.equals(integrateurLangue, that.integrateurLangue) &&
                Objects.equals(integrateurExperience, that.integrateurExperience) &&
                Objects.equals(integrateurAttestation, that.integrateurAttestation) &&
                Objects.equals(integrateurDiplome, that.integrateurDiplome) &&
                Objects.equals(integrateurMembreasso, that.integrateurMembreasso) &&
                Objects.equals(integrateurCanton, that.integrateurCanton) &&
                Objects.equals(integrateurVille, that.integrateurVille) &&
                Objects.equals(integrateurAdresse, that.integrateurAdresse) &&
                Objects.equals(codeMembre, that.codeMembre);
    }

    @Override
    public int hashCode() {

        return Objects.hash(integrateurId, integrateurSouscription, integrateurCritere1, integrateurCritere2, integrateurCritere3, integrateurType, integrateurDate, publier, integrateurPoste, integrateurDocument, integrateurEducation, integrateurAffiliation, integrateurFormation, integrateurLangue, integrateurExperience, integrateurAttestation, integrateurDiplome, integrateurMembreasso, integrateurCanton, integrateurVille, integrateurAdresse, codeMembre);
    }
}
