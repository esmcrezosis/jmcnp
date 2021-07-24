package com.esmc.mcnp.model.acteur;

import com.esmc.mcnp.model.acteur.EuDetailContratLivraisonIrrevocable;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_contrat_livraison_irrevocable database table.
 */
@Entity
@Table(name = "eu_contrat_livraison_irrevocable")
@NamedQuery(name = "EuContratLivraisonIrrevocable.findAll", query = "SELECT e FROM EuContratLivraisonIrrevocable e")
public class EuContratLivraisonIrrevocable implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idContrat;
    private String codeMembre;
    private String typeValidateur;
    private String civilite;
    private String nom;
    private String demeure;
    private String libelleDemeure;
    private String quartier;
    private String boitePostale;
    private String telephone;
    private String typeMaison;
    private String situation;
    private String libelleSituation;
    private String rue;
    private String civiliteRepresentant;
    private String nomRepresentant;
    private String carteOperateur;
    private String numeroRecipce;
    private String siege;
    private String numeroRegistre;
    private int periodeGarde;
    private String chargementProduit;
    private Date dateContrat;
    private byte statut;
    private List<EuDetailContratLivraisonIrrevocable> detailContratLivraisonIrrevocables;

    public EuContratLivraisonIrrevocable() {
    }


    @Id
    @Column(name = "id_contrat")
    public int getIdContrat() {
        return this.idContrat;
    }

    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }


    @Column(name = "chargement_produit")
    public String getChargementProduit() {
        return this.chargementProduit;
    }

    public void setChargementProduit(String chargementProduit) {
        this.chargementProduit = chargementProduit;
    }


    @Column(name = "code_membre")
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(name = "type_validateur")
    public String getTypeValidateur() {
        return typeValidateur;
    }

    public void setTypeValidateur(String typeValidateur) {
        this.typeValidateur = typeValidateur;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDemeure() {
        return demeure;
    }

    public void setDemeure(String demeure) {
        this.demeure = demeure;
    }

    @Column(name = "libelle_demeure")
    public String getLibelleDemeure() {
        return libelleDemeure;
    }

    public void setLibelleDemeure(String libelleDemeure) {
        this.libelleDemeure = libelleDemeure;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    @Column(name = "boite_postale")
    public String getBoitePostale() {
        return boitePostale;
    }

    public void setBoitePostale(String boitePostale) {
        this.boitePostale = boitePostale;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "type_maison")
    public String getTypeMaison() {
        return typeMaison;
    }

    public void setTypeMaison(String typeMaison) {
        this.typeMaison = typeMaison;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    @Column(name = "libelle_situation")
    public String getLibelleSituation() {
        return libelleSituation;
    }

    public void setLibelleSituation(String libelleSituation) {
        this.libelleSituation = libelleSituation;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    @Column(name = "civilite_representant")
    public String getCiviliteRepresentant() {
        return civiliteRepresentant;
    }

    public void setCiviliteRepresentant(String civiliteRepresentant) {
        this.civiliteRepresentant = civiliteRepresentant;
    }

    @Column(name = "nom_representant")
    public String getNomRepresentant() {
        return nomRepresentant;
    }

    public void setNomRepresentant(String nomRepresentant) {
        this.nomRepresentant = nomRepresentant;
    }

    @Column(name = "carte_operateur")
    public String getCarteOperateur() {
        return carteOperateur;
    }

    public void setCarteOperateur(String carteOperateur) {
        this.carteOperateur = carteOperateur;
    }

    @Column(name = "numero_recipice")
    public String getNumeroRecipce() {
        return numeroRecipce;
    }

    public void setNumeroRecipce(String numeroRecipce) {
        this.numeroRecipce = numeroRecipce;
    }

    public String getSiege() {
        return siege;
    }

    public void setSiege(String siege) {
        this.siege = siege;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_contrat")
    public Date getDateContrat() {
        return this.dateContrat;
    }

    public void setDateContrat(Date dateContrat) {
        this.dateContrat = dateContrat;
    }

    @Column(name = "matricule_rccm")
    public String getNumeroRegistre() {
        return this.numeroRegistre;
    }

    public void setNumeroRegistre(String numeroRegistre) {
        this.numeroRegistre = numeroRegistre;
    }


    @Column(name = "periode_garde")
    public int getPeriodeGarde() {
        return this.periodeGarde;
    }

    public void setPeriodeGarde(int periodeGarde) {
        this.periodeGarde = periodeGarde;
    }


    public byte getStatut() {
        return this.statut;
    }

    public void setStatut(byte statut) {
        this.statut = statut;
    }

    @OneToMany(mappedBy = "contrat")
    public List<EuDetailContratLivraisonIrrevocable> getDetailContratLivraisonIrrevocables() {
        return detailContratLivraisonIrrevocables;
    }

    public void setDetailContratLivraisonIrrevocables(List<EuDetailContratLivraisonIrrevocable> detailContratLivraisonIrrevocables) {
        this.detailContratLivraisonIrrevocables = detailContratLivraisonIrrevocables;
    }
}