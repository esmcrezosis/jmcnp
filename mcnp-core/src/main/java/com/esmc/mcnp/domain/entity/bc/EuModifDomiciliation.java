package com.esmc.mcnp.domain.entity.bc;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_modif_domiciliation database table.
 *
 */
@Entity
@Table(name = "eu_modif_domiciliation")
@NamedQuery(name = "EuModifDomiciliation.findAll", query = "SELECT e FROM EuModifDomiciliation e")
public class EuModifDomiciliation implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idModifDom;
    private String codeDomiciliation;
    private String codeMembre;
    private String codeMembreBenef;
    private Date dateModification;
    private Date heureModif;
    private double idUtilisateur;

    public EuModifDomiciliation() {
    }

    @Id
    @Column(name = "id_modif_dom", unique = true, nullable = false)
    public Integer getIdModifDom() {
        return this.idModifDom;
    }

    public void setIdModifDom(Integer idModifDom) {
        this.idModifDom = idModifDom;
    }

    @Column(name = "code_domiciliation", length = 200)
    public String getCodeDomiciliation() {
        return this.codeDomiciliation;
    }

    public void setCodeDomiciliation(String codeDomiciliation) {
        this.codeDomiciliation = codeDomiciliation;
    }

    @Column(name = "code_membre", length = 100)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(name = "code_membre_benef", length = 100)
    public String getCodeMembreBenef() {
        return this.codeMembreBenef;
    }

    public void setCodeMembreBenef(String codeMembreBenef) {
        this.codeMembreBenef = codeMembreBenef;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_modification")
    public Date getDateModification() {
        return this.dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "heure_modif")
    public Date getHeureModif() {
        return this.heureModif;
    }

    public void setHeureModif(Date heureModif) {
        this.heureModif = heureModif;
    }

    @Column(name = "id_utilisateur")
    public double getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(double idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

}
