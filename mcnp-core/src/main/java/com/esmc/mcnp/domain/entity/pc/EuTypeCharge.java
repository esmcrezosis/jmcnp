/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.domain.entity.pc;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "eu_type_charge")
@NamedQueries({
    @NamedQuery(name = "EuTypeCharge.findAll", query = "SELECT e FROM EuTypeCharge e")
    , @NamedQuery(name = "EuTypeCharge.findByIdTypeCharge", query = "SELECT e FROM EuTypeCharge e WHERE e.idTypeCharge = :idTypeCharge")
    , @NamedQuery(name = "EuTypeCharge.findByCodeTypeCharge", query = "SELECT e FROM EuTypeCharge e WHERE e.codeTypeCharge = :codeTypeCharge")
    , @NamedQuery(name = "EuTypeCharge.findByLibelleTypeCharge", query = "SELECT e FROM EuTypeCharge e WHERE e.libelleTypeCharge = :libelleTypeCharge")})
public class EuTypeCharge implements Serializable {

    private static final long serialVersionUID = 1L;
 
    private Integer idTypeCharge;
    private String codeTypeCharge;
    private String libelleTypeCharge;
    private String descTypeCharge;
    private List<EuCharge> euChargeList;

    public EuTypeCharge() {
    }

    public EuTypeCharge(Integer idTypeCharge) {
        this.idTypeCharge = idTypeCharge;
    }

    public EuTypeCharge(Integer idTypeCharge, String codeTypeCharge, String libelleTypeCharge) {
        this.idTypeCharge = idTypeCharge;
        this.codeTypeCharge = codeTypeCharge;
        this.libelleTypeCharge = libelleTypeCharge;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_charge")
    public Integer getIdTypeCharge() {
        return idTypeCharge;
    }

    public void setIdTypeCharge(Integer idTypeCharge) {
        this.idTypeCharge = idTypeCharge;
    }

    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "code_type_charge")
    public String getCodeTypeCharge() {
        return codeTypeCharge;
    }

    public void setCodeTypeCharge(String codeTypeCharge) {
        this.codeTypeCharge = codeTypeCharge;
    }

    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "libelle_type_charge")
    public String getLibelleTypeCharge() {
        return libelleTypeCharge;
    }

    public void setLibelleTypeCharge(String libelleTypeCharge) {
        this.libelleTypeCharge = libelleTypeCharge;
    }

    @Lob
    @Size(max = 65535)
    @Column(name = "desc_type_charge")
    public String getDescTypeCharge() {
        return descTypeCharge;
    }

    public void setDescTypeCharge(String descTypeCharge) {
        this.descTypeCharge = descTypeCharge;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "euTypeCharge")
    public List<EuCharge> getEuChargeList() {
        return euChargeList;
    }

    public void setEuChargeList(List<EuCharge> euChargeList) {
        this.euChargeList = euChargeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypeCharge != null ? idTypeCharge.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EuTypeCharge)) {
            return false;
        }
        EuTypeCharge other = (EuTypeCharge) object;
        return !((this.idTypeCharge == null && other.idTypeCharge != null) || (this.idTypeCharge != null && !this.idTypeCharge.equals(other.idTypeCharge)));
    }

    @Override
    public String toString() {
        return "com.esmc.mcnp.model.pc.EuTypeCharge[ idTypeCharge=" + idTypeCharge + " ]";
    }

}
