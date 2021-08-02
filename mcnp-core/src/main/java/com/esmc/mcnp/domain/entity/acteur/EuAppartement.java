package com.esmc.mcnp.domain.entity.acteur;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.domain.entity.acteur.EuLouer;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

/**
 * The persistent class for the eu_appartement database table.
 *
 */
@Entity
@Table(name = "eu_appartement")
@NamedQuery(name = "EuAppartement.findAll", query = "SELECT e FROM EuAppartement e")
public class EuAppartement implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAppartement;
    private Integer cuisine;
    private Date dateEnregistrement;
    private String descAppart;
    private Integer garage;
    private Date heureEnregistrement;
    private Long idMaison;
    private Integer nbPiece;
    private double prixLocation;
    private String situation;
    private Integer statut;
    private Integer terasse;
    private String typeAppartement;
    private Integer wcDoucheInterne;
    private EuUtilisateur euUtilisateur;
    private List<EuLouer> euLouers;

    public EuAppartement() {
    }

    @Id
    @Column(name = "id_appartement", unique = true, nullable = false)
    public Long getIdAppartement() {
        return this.idAppartement;
    }

    public void setIdAppartement(Long idAppartement) {
        this.idAppartement = idAppartement;
    }

    public Integer getCuisine() {
        return this.cuisine;
    }

    public void setCuisine(Integer cuisine) {
        this.cuisine = cuisine;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_enregistrement")
    public Date getDateEnregistrement() {
        return this.dateEnregistrement;
    }

    public void setDateEnregistrement(Date dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    @Lob
    @Column(name = "desc_appart")
    public String getDescAppart() {
        return this.descAppart;
    }

    public void setDescAppart(String descAppart) {
        this.descAppart = descAppart;
    }

    public Integer getGarage() {
        return this.garage;
    }

    public void setGarage(Integer garage) {
        this.garage = garage;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "heure_enregistrement")
    public Date getHeureEnregistrement() {
        return this.heureEnregistrement;
    }

    public void setHeureEnregistrement(Date heureEnregistrement) {
        this.heureEnregistrement = heureEnregistrement;
    }

    @Column(name = "id_maison", nullable = false)
    public Long getIdMaison() {
        return this.idMaison;
    }

    public void setIdMaison(Long idMaison) {
        this.idMaison = idMaison;
    }

    @Column(name = "nb_piece")
    public Integer getNbPiece() {
        return this.nbPiece;
    }

    public void setNbPiece(Integer nbPiece) {
        this.nbPiece = nbPiece;
    }

    @Column(name = "prix_location")
    public double getPrixLocation() {
        return this.prixLocation;
    }

    public void setPrixLocation(double prixLocation) {
        this.prixLocation = prixLocation;
    }

    @Column(length = 200)
    public String getSituation() {
        return this.situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Integer getStatut() {
        return this.statut;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
    }

    @Column(nullable = false)
    public Integer getTerasse() {
        return this.terasse;
    }

    public void setTerasse(Integer terasse) {
        this.terasse = terasse;
    }

    @Column(name = "type_appartement", length = 200)
    public String getTypeAppartement() {
        return this.typeAppartement;
    }

    public void setTypeAppartement(String typeAppartement) {
        this.typeAppartement = typeAppartement;
    }

    @Column(name = "wc_douche_interne")
    public Integer getWcDoucheInterne() {
        return this.wcDoucheInterne;
    }

    public void setWcDoucheInterne(Integer wcDoucheInterne) {
        this.wcDoucheInterne = wcDoucheInterne;
    }

    //bi-directional many-to-one association to EuUtilisateur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    public EuUtilisateur getEuUtilisateur() {
        return this.euUtilisateur;
    }

    public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
        this.euUtilisateur = euUtilisateur;
    }

    //bi-directional many-to-one association to EuLouer
    @OneToMany(mappedBy = "euAppartement")
    public List<EuLouer> getEuLouers() {
        return this.euLouers;
    }

    public void setEuLouers(List<EuLouer> euLouers) {
        this.euLouers = euLouers;
    }

    public EuLouer addEuLouer(EuLouer euLouer) {
        getEuLouers().add(euLouer);
        euLouer.setEuAppartement(this);

        return euLouer;
    }

    public EuLouer removeEuLouer(EuLouer euLouer) {
        getEuLouers().remove(euLouer);
        euLouer.setEuAppartement(null);

        return euLouer;
    }

}
