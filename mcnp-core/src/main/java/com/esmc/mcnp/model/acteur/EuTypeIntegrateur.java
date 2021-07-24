package com.esmc.mcnp.model.acteur;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "eu_type_integrateur")
public class EuTypeIntegrateur {
    private int idTypeIntegrateur;
    private String libelleTypeIntegrateur;
    private String natureIntegrateur;
    private Integer typeParamBan;
    private Double montantParam;

    @Id
    @Column(name = "id_type_integrateur")
    public int getIdTypeIntegrateur() {
        return idTypeIntegrateur;
    }

    public void setIdTypeIntegrateur(int idTypeIntegrateur) {
        this.idTypeIntegrateur = idTypeIntegrateur;
    }

    @Basic
    @Column(name = "libelle_type_integrateur")
    public String getLibelleTypeIntegrateur() {
        return libelleTypeIntegrateur;
    }

    public void setLibelleTypeIntegrateur(String libelleTypeIntegrateur) {
        this.libelleTypeIntegrateur = libelleTypeIntegrateur;
    }

    @Basic
    @Column(name = "nature_integrateur")
    public String getNatureIntegrateur() {
        return natureIntegrateur;
    }

    public void setNatureIntegrateur(String natureIntegrateur) {
        this.natureIntegrateur = natureIntegrateur;
    }

    @Basic
    @Column(name = "type_param_ban")
    public Integer getTypeParamBan() {
        return typeParamBan;
    }

    public void setTypeParamBan(Integer typeParamBan) {
        this.typeParamBan = typeParamBan;
    }

    @Basic
    @Column(name = "montant_param")
    public Double getMontantParam() {
        return montantParam;
    }

    public void setMontantParam(Double montantParam) {
        this.montantParam = montantParam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EuTypeIntegrateur that = (EuTypeIntegrateur) o;
        return idTypeIntegrateur == that.idTypeIntegrateur &&
                Objects.equals(libelleTypeIntegrateur, that.libelleTypeIntegrateur) &&
                Objects.equals(natureIntegrateur, that.natureIntegrateur) &&
                Objects.equals(typeParamBan, that.typeParamBan) &&
                Objects.equals(montantParam, that.montantParam);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idTypeIntegrateur, libelleTypeIntegrateur, natureIntegrateur, typeParamBan, montantParam);
    }
}
