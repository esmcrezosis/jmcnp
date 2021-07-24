package com.esmc.mcnp.model.oksu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.esmc.mcnp.model.obps.EuGcp;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "eu_revendeur")
public class EuRevendeur implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idRevendeur;
    private String codeMembre;
    private String nomProduit;
    private Double montantSouscription;
    private EuGcp euGcp;

    public EuRevendeur() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_revendeur", unique = true, nullable = false, length = 60)
    public Long getIdRevendeur() {
        return idRevendeur;
    }

    public void setIdRevendeur(Long idRevendeur) {
        this.idRevendeur = idRevendeur;
    }

    @Column(name = "code_membre")
    public String getCodeMembre() {
        return codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(name = "nom_produit")
    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    @Column(name = "montant_souscription")
    public Double getMontantSouscription() {
        return montantSouscription;
    }

    public void setMontantSouscription(Double montantSouscription) {
        this.montantSouscription = montantSouscription;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gcp", nullable = false)
    public EuGcp getEuGcp() {
        return euGcp;
    }

    public void setEuGcp(EuGcp euGcp) {
        this.euGcp = euGcp;
    }

}
