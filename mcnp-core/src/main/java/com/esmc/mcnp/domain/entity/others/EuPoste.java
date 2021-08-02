package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_poste database table.
 *
 */
@Entity
@Table(name = "eu_poste")
@NamedQuery(name = "EuPoste.findAll", query = "SELECT e FROM EuPoste e")
public class EuPoste implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer posteId;
    private Integer posteTache;
    private Long posteUtilisateur;

    public EuPoste() {
    }

    @Id
    @Column(name = "poste_id", unique = true, nullable = false)
    public Integer getPosteId() {
        return this.posteId;
    }

    public void setPosteId(Integer posteId) {
        this.posteId = posteId;
    }

    @Column(name = "poste_tache")
    public Integer getPosteTache() {
        return this.posteTache;
    }

    public void setPosteTache(Integer posteTache) {
        this.posteTache = posteTache;
    }

    @Column(name = "poste_utilisateur")
    public Long getPosteUtilisateur() {
        return this.posteUtilisateur;
    }

    public void setPosteUtilisateur(Long posteUtilisateur) {
        this.posteUtilisateur = posteUtilisateur;
    }

}
