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
 * The persistent class for the eu_section database table.
 *
 */
@Entity
@Table(name = "eu_section")
@NamedQuery(name = "EuSection.findAll", query = "SELECT e FROM EuSection e")
public class EuSection implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idSection;
    private Date dateCreation;
    private Integer idPays;
    private Long idUtilisateur;
    private String nomSection;

    public EuSection() {
    }

    @Id
    @Column(name = "id_section", unique = true, nullable = false)
    public Integer getIdSection() {
        return this.idSection;
    }

    public void setIdSection(Integer idSection) {
        this.idSection = idSection;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_creation")
    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Column(name = "id_pays")
    public Integer getIdPays() {
        return this.idPays;
    }

    public void setIdPays(Integer idPays) {
        this.idPays = idPays;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "nom_section", length = 150)
    public String getNomSection() {
        return this.nomSection;
    }

    public void setNomSection(String nomSection) {
        this.nomSection = nomSection;
    }

}
