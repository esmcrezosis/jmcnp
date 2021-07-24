package com.esmc.mcnp.model.cm;

import com.esmc.mcnp.model.cm.EuAncienCompte;
import com.esmc.mcnp.model.cm.EuCompte;
import com.esmc.mcnp.model.cm.EuCompteGeneral;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the eu_type_compte database table.
 *
 */
@Entity
@Table(name = "eu_type_compte")
@NamedQuery(name = "EuTypeCompte.findAll", query = "SELECT e FROM EuTypeCompte e")
public class EuTypeCompte implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeTypeCompte;
    private String descType;
    private String libType;
    private List<EuAncienCompte> euAncienComptes;
    private List<EuCompte> euComptes;
    private List<EuCompteGeneral> euCompteGenerals;

    public EuTypeCompte() {
    }

    public EuTypeCompte(String codeTypeCompte) {
        this.codeTypeCompte = codeTypeCompte;
    }

    @Id
    @Column(name = "code_type_compte", unique = true, nullable = false, length = 16)
    public String getCodeTypeCompte() {
        return this.codeTypeCompte;
    }

    public void setCodeTypeCompte(String codeTypeCompte) {
        this.codeTypeCompte = codeTypeCompte;
    }

    @Column(name = "desc_type", length = 100)
    public String getDescType() {
        return this.descType;
    }

    public void setDescType(String descType) {
        this.descType = descType;
    }

    @Column(name = "lib_type", length = 100)
    public String getLibType() {
        return this.libType;
    }

    public void setLibType(String libType) {
        this.libType = libType;
    }

    //bi-directional many-to-one association to EuAncienCompte
    @JsonIgnore
    @OneToMany(mappedBy = "euTypeCompte")
    public List<EuAncienCompte> getEuAncienComptes() {
        return this.euAncienComptes;
    }

    public void setEuAncienComptes(List<EuAncienCompte> euAncienComptes) {
        this.euAncienComptes = euAncienComptes;
    }

    public EuAncienCompte addEuAncienCompte(EuAncienCompte euAncienCompte) {
        getEuAncienComptes().add(euAncienCompte);
        euAncienCompte.setEuTypeCompte(this);

        return euAncienCompte;
    }

    public EuAncienCompte removeEuAncienCompte(EuAncienCompte euAncienCompte) {
        getEuAncienComptes().remove(euAncienCompte);
        euAncienCompte.setEuTypeCompte(null);

        return euAncienCompte;
    }

    //bi-directional many-to-one association to EuCompte
    @JsonIgnore
    @OneToMany(mappedBy = "euTypeCompte")
    public List<EuCompte> getEuComptes() {
        return this.euComptes;
    }

    public void setEuComptes(List<EuCompte> euComptes) {
        this.euComptes = euComptes;
    }

    public EuCompte addEuCompte(EuCompte euCompte) {
        getEuComptes().add(euCompte);
        euCompte.setEuTypeCompte(this);

        return euCompte;
    }

    public EuCompte removeEuCompte(EuCompte euCompte) {
        getEuComptes().remove(euCompte);
        euCompte.setEuTypeCompte(null);

        return euCompte;
    }

    //bi-directional many-to-one association to EuCompteGeneral
    @JsonIgnore
    @OneToMany(mappedBy = "euTypeCompte")
    public List<EuCompteGeneral> getEuCompteGenerals() {
        return this.euCompteGenerals;
    }

    public void setEuCompteGenerals(List<EuCompteGeneral> euCompteGenerals) {
        this.euCompteGenerals = euCompteGenerals;
    }

    public EuCompteGeneral addEuCompteGeneral(EuCompteGeneral euCompteGeneral) {
        getEuCompteGenerals().add(euCompteGeneral);
        euCompteGeneral.setEuTypeCompte(this);

        return euCompteGeneral;
    }

    public EuCompteGeneral removeEuCompteGeneral(EuCompteGeneral euCompteGeneral) {
        getEuCompteGenerals().remove(euCompteGeneral);
        euCompteGeneral.setEuTypeCompte(null);

        return euCompteGeneral;
    }

}
