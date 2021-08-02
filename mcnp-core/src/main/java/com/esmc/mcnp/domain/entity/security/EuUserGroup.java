package com.esmc.mcnp.domain.entity.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the eu_user_group database table.
 *
 */
@Entity
@Table(name = "eu_user_group")
@NamedQuery(name = "EuUserGroup.findAll", query = "SELECT e FROM EuUserGroup e")
public class EuUserGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeGroupe;
    private Integer idCmUtilisateur;
    private String libelleGroupe;
    private EuUserGroup euUserGroupParent;
    private List<EuUserGroup> euUserGroupsEnfants;
    private List<EuUtilisateur> euUtilisateurs;

    public EuUserGroup() {
    }

    @Id
    @Column(name = "code_groupe", unique = true, nullable = false, length = 100)
    public String getCodeGroupe() {
        return this.codeGroupe;
    }

    public void setCodeGroupe(String codeGroupe) {
        this.codeGroupe = codeGroupe;
    }

    @Column(name = "id_cm_utilisateur")
    public Integer getIdCmUtilisateur() {
        return this.idCmUtilisateur;
    }

    public void setIdCmUtilisateur(Integer idCmUtilisateur) {
        this.idCmUtilisateur = idCmUtilisateur;
    }

    @Column(name = "libelle_groupe", length = 200)
    public String getLibelleGroupe() {
        return this.libelleGroupe;
    }

    public void setLibelleGroupe(String libelleGroupe) {
        this.libelleGroupe = libelleGroupe;
    }

    //bi-directional many-to-one association to EuUserGroup
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_groupe_parent")
    public EuUserGroup getEuUserGroupParent() {
        return this.euUserGroupParent;
    }

    public void setEuUserGroupParent(EuUserGroup euUserGroupParent) {
        this.euUserGroupParent = euUserGroupParent;
    }

    //bi-directional many-to-one association to EuUserGroup
    @JsonIgnore
    @OneToMany(mappedBy = "euUserGroupParent", fetch = FetchType.LAZY)
    public List<EuUserGroup> getEuUserGroupsEnfants() {
        return this.euUserGroupsEnfants;
    }

    public void setEuUserGroupsEnfants(List<EuUserGroup> euUserGroupsEnfants) {
        this.euUserGroupsEnfants = euUserGroupsEnfants;
    }

    public EuUserGroup addEuUserGroupsEnfant(EuUserGroup euUserGroupsEnfant) {
        getEuUserGroupsEnfants().add(euUserGroupsEnfant);
        euUserGroupsEnfant.setEuUserGroupParent(this);

        return euUserGroupsEnfant;
    }

    public EuUserGroup removeEuUserGroupsEnfant(EuUserGroup euUserGroupsEnfant) {
        getEuUserGroupsEnfants().remove(euUserGroupsEnfant);
        euUserGroupsEnfant.setEuUserGroupParent(null);

        return euUserGroupsEnfant;
    }

    //bi-directional many-to-one association to EuUtilisateur
    @JsonIgnore
    @OneToMany(mappedBy = "euUserGroup")
    public List<EuUtilisateur> getEuUtilisateurs() {
        return this.euUtilisateurs;
    }

    public void setEuUtilisateurs(List<EuUtilisateur> euUtilisateurs) {
        this.euUtilisateurs = euUtilisateurs;
    }

    public EuUtilisateur addEuUtilisateur(EuUtilisateur euUtilisateur) {
        getEuUtilisateurs().add(euUtilisateur);
        euUtilisateur.setEuUserGroup(this);

        return euUtilisateur;
    }

    public EuUtilisateur removeEuUtilisateur(EuUtilisateur euUtilisateur) {
        getEuUtilisateurs().remove(euUtilisateur);
        euUtilisateur.setEuUserGroup(null);

        return euUtilisateur;
    }

}
