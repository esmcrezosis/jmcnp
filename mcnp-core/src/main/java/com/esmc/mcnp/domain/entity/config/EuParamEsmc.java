package com.esmc.mcnp.domain.entity.config;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_param_esmc database table.
 *
 */
@Entity
@Table(name = "eu_param_esmc")
@NamedQuery(name = "EuParamEsmc.findAll", query = "SELECT e FROM EuParamEsmc e")
public class EuParamEsmc implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idParam;
    private String libelleParam;
    private String valeurParam;

    public EuParamEsmc() {
    }

    @Id
    @Column(name = "id_param", unique = true, nullable = false)
    public Integer getIdParam() {
        return this.idParam;
    }

    public void setIdParam(Integer idParam) {
        this.idParam = idParam;
    }

    @Column(name = "libelle_param", length = 250)
    public String getLibelleParam() {
        return this.libelleParam;
    }

    public void setLibelleParam(String libelleParam) {
        this.libelleParam = libelleParam;
    }

    @Column(name = "valeur_param", length = 40)
    public String getValeurParam() {
        return this.valeurParam;
    }

    public void setValeurParam(String valeurParam) {
        this.valeurParam = valeurParam;
    }

}
