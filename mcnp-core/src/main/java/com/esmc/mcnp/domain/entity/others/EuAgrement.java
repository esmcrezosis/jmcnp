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
 * The persistent class for the eu_agrement database table.
 *
 */
@Entity
@Table(name = "eu_agrement")
@NamedQuery(name = "EuAgrement.findAll", query = "SELECT e FROM EuAgrement e")
public class EuAgrement implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAgrement;
    private String celAgrement;
    private String codeMembreMorale;
    private String codeMembreMoraleAgrement;
    private Date dateAgrement;
    private String descAgrement;
    private int idFiliere;
    private Integer idTypeActeur;
    private Integer idTypeAgrement;
    private int idTypeCreneau;
    private Long idUtilisateur;
    private String libelleAgrement;
    private String numAgrement;

    public EuAgrement() {
    }

    @Id
    @Column(name = "id_agrement", unique = true, nullable = false)
    public Long getIdAgrement() {
        return this.idAgrement;
    }

    public void setIdAgrement(Long idAgrement) {
        this.idAgrement = idAgrement;
    }

    @Column(name = "cel_agrement")
    public String getCelAgrement() {
        return this.celAgrement;
    }

    public void setCelAgrement(String celAgrement) {
        this.celAgrement = celAgrement;
    }

    @Column(name = "code_membre_morale", length = 100)
    public String getCodeMembreMorale() {
        return this.codeMembreMorale;
    }

    public void setCodeMembreMorale(String codeMembreMorale) {
        this.codeMembreMorale = codeMembreMorale;
    }

    @Column(name = "code_membre_morale_agrement", length = 100)
    public String getCodeMembreMoraleAgrement() {
        return this.codeMembreMoraleAgrement;
    }

    public void setCodeMembreMoraleAgrement(String codeMembreMoraleAgrement) {
        this.codeMembreMoraleAgrement = codeMembreMoraleAgrement;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_agrement")
    public Date getDateAgrement() {
        return this.dateAgrement;
    }

    public void setDateAgrement(Date dateAgrement) {
        this.dateAgrement = dateAgrement;
    }

    @Column(name = "desc_agrement", length = 500)
    public String getDescAgrement() {
        return this.descAgrement;
    }

    public void setDescAgrement(String descAgrement) {
        this.descAgrement = descAgrement;
    }

    @Column(name = "id_filiere")
    public int getIdFiliere() {
        return this.idFiliere;
    }

    public void setIdFiliere(int idFiliere) {
        this.idFiliere = idFiliere;
    }

    @Column(name = "id_type_acteur")
    public Integer getIdTypeActeur() {
        return this.idTypeActeur;
    }

    public void setIdTypeActeur(Integer idTypeActeur) {
        this.idTypeActeur = idTypeActeur;
    }

    @Column(name = "id_type_agrement")
    public Integer getIdTypeAgrement() {
        return this.idTypeAgrement;
    }

    public void setIdTypeAgrement(Integer idTypeAgrement) {
        this.idTypeAgrement = idTypeAgrement;
    }

    @Column(name = "id_type_creneau")
    public int getIdTypeCreneau() {
        return this.idTypeCreneau;
    }

    public void setIdTypeCreneau(int idTypeCreneau) {
        this.idTypeCreneau = idTypeCreneau;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "libelle_agrement", length = 250)
    public String getLibelleAgrement() {
        return this.libelleAgrement;
    }

    public void setLibelleAgrement(String libelleAgrement) {
        this.libelleAgrement = libelleAgrement;
    }

    @Column(name = "num_agrement", length = 180)
    public String getNumAgrement() {
        return this.numAgrement;
    }

    public void setNumAgrement(String numAgrement) {
        this.numAgrement = numAgrement;
    }

}
