package com.esmc.mcnp.model.others;

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
 * The persistent class for the eu_acheteur database table.
 *
 */
@Entity
@Table(name = "eu_acheteur")
@NamedQuery(name = "EuAcheteur.findAll", query = "SELECT e FROM EuAcheteur e")
public class EuAcheteur implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer acheteurId;
    private String acheteurBanque;
    private String acheteurCel;
    private String acheteurCodeMembre;
    private Date acheteurDate;
    private String acheteurNom;
    private String acheteurNumero;
    private String acheteurPrenom;
    private String acheteurRaisonSociale;
    private String acheteurType;
    private double montTransfert;
    private Integer publier;
    private String typeTransfert;

    public EuAcheteur() {
    }

    @Id
    @Column(name = "acheteur_id", unique = true, nullable = false)
    public Integer getAcheteurId() {
        return this.acheteurId;
    }

    public void setAcheteurId(Integer acheteurId) {
        this.acheteurId = acheteurId;
    }

    @Column(name = "acheteur_banque", length = 10)
    public String getAcheteurBanque() {
        return this.acheteurBanque;
    }

    public void setAcheteurBanque(String acheteurBanque) {
        this.acheteurBanque = acheteurBanque;
    }

    @Column(name = "acheteur_cel", length = 20)
    public String getAcheteurCel() {
        return this.acheteurCel;
    }

    public void setAcheteurCel(String acheteurCel) {
        this.acheteurCel = acheteurCel;
    }

    @Column(name = "acheteur_code_membre", length = 25)
    public String getAcheteurCodeMembre() {
        return this.acheteurCodeMembre;
    }

    public void setAcheteurCodeMembre(String acheteurCodeMembre) {
        this.acheteurCodeMembre = acheteurCodeMembre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "acheteur_date")
    public Date getAcheteurDate() {
        return this.acheteurDate;
    }

    public void setAcheteurDate(Date acheteurDate) {
        this.acheteurDate = acheteurDate;
    }

    @Column(name = "acheteur_nom", length = 255)
    public String getAcheteurNom() {
        return this.acheteurNom;
    }

    public void setAcheteurNom(String acheteurNom) {
        this.acheteurNom = acheteurNom;
    }

    @Column(name = "acheteur_numero", length = 255)
    public String getAcheteurNumero() {
        return this.acheteurNumero;
    }

    public void setAcheteurNumero(String acheteurNumero) {
        this.acheteurNumero = acheteurNumero;
    }

    @Column(name = "acheteur_prenom", length = 255)
    public String getAcheteurPrenom() {
        return this.acheteurPrenom;
    }

    public void setAcheteurPrenom(String acheteurPrenom) {
        this.acheteurPrenom = acheteurPrenom;
    }

    @Column(name = "acheteur_raison_sociale", length = 255)
    public String getAcheteurRaisonSociale() {
        return this.acheteurRaisonSociale;
    }

    public void setAcheteurRaisonSociale(String acheteurRaisonSociale) {
        this.acheteurRaisonSociale = acheteurRaisonSociale;
    }

    @Column(name = "acheteur_type", length = 25)
    public String getAcheteurType() {
        return this.acheteurType;
    }

    public void setAcheteurType(String acheteurType) {
        this.acheteurType = acheteurType;
    }

    @Column(name = "mont_transfert")
    public double getMontTransfert() {
        return this.montTransfert;
    }

    public void setMontTransfert(double montTransfert) {
        this.montTransfert = montTransfert;
    }

    public Integer getPublier() {
        return this.publier;
    }

    public void setPublier(Integer publier) {
        this.publier = publier;
    }

    @Column(name = "type_transfert", length = 11)
    public String getTypeTransfert() {
        return this.typeTransfert;
    }

    public void setTypeTransfert(String typeTransfert) {
        this.typeTransfert = typeTransfert;
    }

}
