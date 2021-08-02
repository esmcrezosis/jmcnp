package com.esmc.mcnp.domain.entity.acteur;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the eu_type_creneau database table.
 *
 */
@Entity
@Table(name = "eu_type_creneau")
@NamedQuery(name = "EuTypeCreneau.findAll", query = "SELECT e FROM EuTypeCreneau e")
public class EuTypeCreneau implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idTypeCreneau;
    private String libelleTypeCreneau;
    private List<EuCreneaux> euCreneauxs;

    public EuTypeCreneau() {
    }

    @Id
    @Column(name = "id_type_creneau", unique = true, nullable = false)
    public Integer getIdTypeCreneau() {
        return this.idTypeCreneau;
    }

    public void setIdTypeCreneau(Integer idTypeCreneau) {
        this.idTypeCreneau = idTypeCreneau;
    }

    @Column(name = "libelle_type_creneau", nullable = false, length = 100)
    public String getLibelleTypeCreneau() {
        return this.libelleTypeCreneau;
    }

    public void setLibelleTypeCreneau(String libelleTypeCreneau) {
        this.libelleTypeCreneau = libelleTypeCreneau;
    }

    //bi-directional many-to-one association to EuCreneaux
    @OneToMany(mappedBy = "euTypeCreneau")
    public List<EuCreneaux> getEuCreneauxs() {
        return this.euCreneauxs;
    }

    public void setEuCreneauxs(List<EuCreneaux> euCreneauxs) {
        this.euCreneauxs = euCreneauxs;
    }

    public EuCreneaux addEuCreneaux(EuCreneaux euCreneaux) {
        getEuCreneauxs().add(euCreneaux);
        euCreneaux.setEuTypeCreneau(this);

        return euCreneaux;
    }

    public EuCreneaux removeEuCreneaux(EuCreneaux euCreneaux) {
        getEuCreneauxs().remove(euCreneaux);
        euCreneaux.setEuTypeCreneau(null);

        return euCreneaux;
    }

}
