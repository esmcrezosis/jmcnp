package com.esmc.mcnp.model.acteur;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_type_agrement database table.
 *
 */
@Entity
@Table(name = "eu_type_agrement")
@NamedQuery(name = "EuTypeAgrement.findAll", query = "SELECT e FROM EuTypeAgrement e")
public class EuTypeAgrement implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idTypeAgrement;
    private String libelleTypeAgrement;

    public EuTypeAgrement() {
    }

    @Id
    @Column(name = "id_type_agrement", unique = true, nullable = false)
    public Integer getIdTypeAgrement() {
        return this.idTypeAgrement;
    }

    public void setIdTypeAgrement(Integer idTypeAgrement) {
        this.idTypeAgrement = idTypeAgrement;
    }

    @Column(name = "libelle_type_agrement", length = 100)
    public String getLibelleTypeAgrement() {
        return this.libelleTypeAgrement;
    }

    public void setLibelleTypeAgrement(String libelleTypeAgrement) {
        this.libelleTypeAgrement = libelleTypeAgrement;
    }

}
