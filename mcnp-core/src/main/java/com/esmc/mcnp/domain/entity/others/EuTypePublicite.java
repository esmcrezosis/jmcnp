package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_type_publicite database table.
 *
 */
@Entity
@Table(name = "eu_type_publicite")
@NamedQuery(name = "EuTypePublicite.findAll", query = "SELECT e FROM EuTypePublicite e")
public class EuTypePublicite implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idTypePublicite;
    private String libelleTypePublicite;

    public EuTypePublicite() {
    }

    @Id
    @Column(name = "id_type_publicite", unique = true, nullable = false)
    public Integer getIdTypePublicite() {
        return this.idTypePublicite;
    }

    public void setIdTypePublicite(Integer idTypePublicite) {
        this.idTypePublicite = idTypePublicite;
    }

    @Column(name = "libelle_type_publicite", length = 255)
    public String getLibelleTypePublicite() {
        return this.libelleTypePublicite;
    }

    public void setLibelleTypePublicite(String libelleTypePublicite) {
        this.libelleTypePublicite = libelleTypePublicite;
    }

}
