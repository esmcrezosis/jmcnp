package com.esmc.mcnp.model.acteur;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the eu_type_contrat database table.
 *
 */
@Entity
@Table(name = "eu_type_contrat")
@NamedQuery(name = "EuTypeContrat.findAll", query = "SELECT e FROM EuTypeContrat e")
public class EuTypeContrat implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idTypeContrat;
    private String libelleTypeContrat;
    private List<EuContrat> euContrats;

    public EuTypeContrat() {
    }

    @Id
    @Column(name = "id_type_contrat", unique = true, nullable = false)
    public Integer getIdTypeContrat() {
        return this.idTypeContrat;
    }

    public void setIdTypeContrat(Integer idTypeContrat) {
        this.idTypeContrat = idTypeContrat;
    }

    @Column(name = "libelle_type_contrat", nullable = false, length = 100)
    public String getLibelleTypeContrat() {
        return this.libelleTypeContrat;
    }

    public void setLibelleTypeContrat(String libelleTypeContrat) {
        this.libelleTypeContrat = libelleTypeContrat;
    }

    //bi-directional many-to-one association to EuContrat
    @OneToMany(mappedBy = "euTypeContrat")
    public List<EuContrat> getEuContrats() {
        return this.euContrats;
    }

    public void setEuContrats(List<EuContrat> euContrats) {
        this.euContrats = euContrats;
    }

    public EuContrat addEuContrat(EuContrat euContrat) {
        getEuContrats().add(euContrat);
        euContrat.setEuTypeContrat(this);

        return euContrat;
    }

    public EuContrat removeEuContrat(EuContrat euContrat) {
        getEuContrats().remove(euContrat);
        euContrat.setEuTypeContrat(null);

        return euContrat;
    }

}
