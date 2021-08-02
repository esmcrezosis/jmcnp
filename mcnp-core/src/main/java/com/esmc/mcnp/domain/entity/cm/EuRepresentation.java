package com.esmc.mcnp.domain.entity.cm;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_representation database table.
 *
 */
@Entity
@Table(name = "eu_representation")
@NamedQuery(name = "EuRepresentation.findAll", query = "SELECT e FROM EuRepresentation e")
public class EuRepresentation implements Serializable {

    private static final long serialVersionUID = 1L;
    private EuRepresentationPK id;
    private Date dateCreation;
    private String etat;
    private Long idUtilisateur;
    private String titre;
    private EuMembre euMembre;
    private EuMembreMorale euMembreMorale;

    public EuRepresentation() {
    }

    @EmbeddedId
    public EuRepresentationPK getId() {
        return this.id;
    }

    public void setId(EuRepresentationPK id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_creation")
    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Column(length = 80)
    public String getEtat() {
        return this.etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(length = 100)
    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    //bi-directional many-to-one association to EuMembre
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code_membre", nullable = false, insertable = false, updatable = false)
    public EuMembre getEuMembre() {
        return this.euMembre;
    }

    public void setEuMembre(EuMembre euMembre) {
        this.euMembre = euMembre;
    }

    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre_morale", nullable = false, insertable = false, updatable = false)
    public EuMembreMorale getEuMembreMorale() {
        return this.euMembreMorale;
    }

    public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
        this.euMembreMorale = euMembreMorale;
    }

}
