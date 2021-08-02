package com.esmc.mcnp.model.acteur;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_type_maison database table.
 *
 */
@Entity
@Table(name = "eu_type_maison")
@NamedQuery(name = "EuTypeMaison.findAll", query = "SELECT e FROM EuTypeMaison e")
public class EuTypeMaison implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTypeMaison;
    private String libTypeMaison;

    public EuTypeMaison() {
    }

    @Id
    @Column(name = "id_type_maison", unique = true, nullable = false)
    public Long getIdTypeMaison() {
        return this.idTypeMaison;
    }

    public void setIdTypeMaison(Long idTypeMaison) {
        this.idTypeMaison = idTypeMaison;
    }

    @Column(name = "lib_type_maison", length = 100)
    public String getLibTypeMaison() {
        return this.libTypeMaison;
    }

    public void setLibTypeMaison(String libTypeMaison) {
        this.libTypeMaison = libTypeMaison;
    }

}
