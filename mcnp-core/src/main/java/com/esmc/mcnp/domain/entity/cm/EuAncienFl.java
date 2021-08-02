package com.esmc.mcnp.domain.entity.cm;

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
 * The persistent class for the eu_ancien_fl database table.
 *
 */
@Entity
@Table(name = "eu_ancien_fl")
@NamedQuery(name = "EuAncienFl.findAll", query = "SELECT e FROM EuAncienFl e")
public class EuAncienFl implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeFl;
    private String codeMembre;
    private String creditcode;
    private Date dateFl;
    private Date heureFl;
    private Long idUtilisateur;
    private double montFl;

    public EuAncienFl() {
    }

    @Id
    @Column(name = "code_fl", unique = true, nullable = false, length = 100)
    public String getCodeFl() {
        return this.codeFl;
    }

    public void setCodeFl(String codeFl) {
        this.codeFl = codeFl;
    }

    @Column(name = "code_membre", length = 100)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(length = 200)
    public String getCreditcode() {
        return this.creditcode;
    }

    public void setCreditcode(String creditcode) {
        this.creditcode = creditcode;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fl")
    public Date getDateFl() {
        return this.dateFl;
    }

    public void setDateFl(Date dateFl) {
        this.dateFl = dateFl;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "heure_fl")
    public Date getHeureFl() {
        return this.heureFl;
    }

    public void setHeureFl(Date heureFl) {
        this.heureFl = heureFl;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "mont_fl")
    public double getMontFl() {
        return this.montFl;
    }

    public void setMontFl(double montFl) {
        this.montFl = montFl;
    }

}
