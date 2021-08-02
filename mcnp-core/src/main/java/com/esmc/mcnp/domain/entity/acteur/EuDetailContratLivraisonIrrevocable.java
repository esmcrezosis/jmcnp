package com.esmc.mcnp.domain.entity.acteur;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by USER on 13/03/2017.
 */
@Entity
@Table(name = "eu_detail_contrat_livraison_irrevocable")
public class EuDetailContratLivraisonIrrevocable implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idDetailContrat;
    private EuContratLivraisonIrrevocable contrat;
    private String libelleProduit;
    private Double montantProduit;
    private Integer quantite;
    private Double prixUnitaire;
    private boolean statut;

    @Id
    @Column(name = "id_detail_contrat")
    public Long getIdDetailContrat() {
        return idDetailContrat;
    }

    public void setIdDetailContrat(Long idDetailContrat) {
        this.idDetailContrat = idDetailContrat;
    }

    @ManyToOne
    @JoinColumn(name = "id_contrat")
    public EuContratLivraisonIrrevocable getContrat() {
        return contrat;
    }

    public void setContrat(EuContratLivraisonIrrevocable contrat) {
        this.contrat = contrat;
    }

    @Column(name = "libelle_produit")
    public String getLibelleProduit() {
        return libelleProduit;
    }

    public void setLibelleProduit(String libelleProduit) {
        this.libelleProduit = libelleProduit;
    }

    @Column(name = "montant_produit")
    public Double getMontantProduit() {
        return montantProduit;
    }

    public void setMontantProduit(Double montantProduit) {
        this.montantProduit = montantProduit;
    }

    @Column(name = "quantite")
    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    @Column(name = "prix_unitaire")
    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    @Column(name = "statut")
    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }
}
