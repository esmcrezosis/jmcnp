/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.esmc.mcnp.domain.entity.acteur.EuFiliere;
import com.esmc.mcnp.domain.entity.bc.EuTypeCredit;

/**
 *
 * @author Administrateur
 */
@Entity
@Table(name = "eu_filiere_type_credit")
@NamedQueries({
    @NamedQuery(name = "EuFiliereTypeCredit.findAll", query = "SELECT e FROM EuFiliereTypeCredit e")})
public class EuFiliereTypeCredit implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idFiliereTypeCredit;
    private EuTypeCredit euTypeCredit;
    private EuFiliere euFiliere;

    public EuFiliereTypeCredit() {
    }

    public EuFiliereTypeCredit(Integer idFiliereTypeCredit) {
        this.idFiliereTypeCredit = idFiliereTypeCredit;
    }

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_filiere_type_credit")
    public Integer getIdFiliereTypeCredit() {
        return idFiliereTypeCredit;
    }

    public void setIdFiliereTypeCredit(Integer idFiliereTypeCredit) {
        this.idFiliereTypeCredit = idFiliereTypeCredit;
    }

    @JoinColumn(name = "code_type_credit", referencedColumnName = "code_type_credit")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    public EuTypeCredit getEuTypeCredit() {
        return euTypeCredit;
    }

    public void setEuTypeCredit(EuTypeCredit euTypeCredit) {
        this.euTypeCredit = euTypeCredit;
    }

    @JoinColumn(name = "id_filiere", referencedColumnName = "id_filiere")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    public EuFiliere getEuFiliere() {
        return euFiliere;
    }

    public void setEuFiliere(EuFiliere euFiliere) {
        this.euFiliere = euFiliere;
    }

}
