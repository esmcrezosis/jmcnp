package com.esmc.mcnp.model.cm;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the eu_religion database table.
 *
 */
@Entity
@Table(name = "eu_religion")
@NamedQuery(name = "EuReligion.findAll", query = "SELECT e FROM EuReligion e")
public class EuReligion implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idReligionMembre;
    private String libelleReligion;
    private List<EuAncienMembre> euAncienMembres;
    private List<EuMembre> euMembres;

    public EuReligion() {
    }

    @Id
    @Column(name = "id_religion_membre", unique = true, nullable = false)
    public Integer getIdReligionMembre() {
        return this.idReligionMembre;
    }

    public void setIdReligionMembre(Integer idReligionMembre) {
        this.idReligionMembre = idReligionMembre;
    }

    @Column(name = "libelle_religion", length = 200)
    public String getLibelleReligion() {
        return this.libelleReligion;
    }

    public void setLibelleReligion(String libelleReligion) {
        this.libelleReligion = libelleReligion;
    }

    //bi-directional many-to-one association to EuAncienMembre
    @JsonIgnore
    @OneToMany(mappedBy = "euReligion")
    public List<EuAncienMembre> getEuAncienMembres() {
        return this.euAncienMembres;
    }

    public void setEuAncienMembres(List<EuAncienMembre> euAncienMembres) {
        this.euAncienMembres = euAncienMembres;
    }

    public EuAncienMembre addEuAncienMembre(EuAncienMembre euAncienMembre) {
        getEuAncienMembres().add(euAncienMembre);
        euAncienMembre.setEuReligion(this);

        return euAncienMembre;
    }

    public EuAncienMembre removeEuAncienMembre(EuAncienMembre euAncienMembre) {
        getEuAncienMembres().remove(euAncienMembre);
        euAncienMembre.setEuReligion(null);

        return euAncienMembre;
    }

    //bi-directional many-to-one association to EuMembre
    @JsonIgnore
    @OneToMany(mappedBy = "euReligion")
    public List<EuMembre> getEuMembres() {
        return this.euMembres;
    }

    public void setEuMembres(List<EuMembre> euMembres) {
        this.euMembres = euMembres;
    }

    public EuMembre addEuMembre(EuMembre euMembre) {
        getEuMembres().add(euMembre);
        euMembre.setEuReligion(this);

        return euMembre;
    }

    public EuMembre removeEuMembre(EuMembre euMembre) {
        getEuMembres().remove(euMembre);
        euMembre.setEuReligion(null);

        return euMembre;
    }

}
