package com.esmc.mcnp.model.others;

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
 * The persistent class for the eu_activite database table.
 *
 */
@Entity
@Table(name = "eu_activite")
@NamedQuery(name = "EuActivite.findAll", query = "SELECT e FROM EuActivite e")
public class EuActivite implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeActivite;
    private Date dateCreation;
    private Integer idFiliere;
    private Long idUtilisateur;
    private String nomActivite;

    public EuActivite() {
    }

    @Id
    @Column(name = "code_activite", unique = true, nullable = false, length = 20)
    public String getCodeActivite() {
        return this.codeActivite;
    }

    public void setCodeActivite(String codeActivite) {
        this.codeActivite = codeActivite;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_creation")
    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Column(name = "id_filiere")
    public Integer getIdFiliere() {
        return this.idFiliere;
    }

    public void setIdFiliere(Integer idFiliere) {
        this.idFiliere = idFiliere;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "nom_activite", length = 250)
    public String getNomActivite() {
        return this.nomActivite;
    }

    public void setNomActivite(String nomActivite) {
        this.nomActivite = nomActivite;
    }

}
