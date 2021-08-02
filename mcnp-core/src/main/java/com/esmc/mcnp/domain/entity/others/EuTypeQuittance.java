package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_type_quittance database table.
 *
 */
@Entity
@Table(name = "eu_type_quittance")
@NamedQuery(name = "EuTypeQuittance.findAll", query = "SELECT e FROM EuTypeQuittance e")
public class EuTypeQuittance implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idTypeQuittance;
    private String libelleTypeQuittance;

    public EuTypeQuittance() {
    }

    @Id
    @Column(name = "id_type_quittance", unique = true, nullable = false)
    public Integer getIdTypeQuittance() {
        return this.idTypeQuittance;
    }

    public void setIdTypeQuittance(Integer idTypeQuittance) {
        this.idTypeQuittance = idTypeQuittance;
    }

    @Column(name = "libelle_type_quittance", length = 200)
    public String getLibelleTypeQuittance() {
        return this.libelleTypeQuittance;
    }

    public void setLibelleTypeQuittance(String libelleTypeQuittance) {
        this.libelleTypeQuittance = libelleTypeQuittance;
    }

}
