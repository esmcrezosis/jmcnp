package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_type_echange database table.
 *
 */
@Entity
@Table(name = "eu_type_echange")
@NamedQuery(name = "EuTypeEchange.findAll", query = "SELECT e FROM EuTypeEchange e")
public class EuTypeEchange implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer disable;
    private String libelle;

    public EuTypeEchange() {
    }

    @Id
    @Column(unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDisable() {
        return this.disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

    @Column(length = 255)
    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}
