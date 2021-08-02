package com.esmc.mcnp.domain.entity.ksu;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_prix_carte database table.
 *
 */
@Entity
@Table(name = "eu_prix_carte")
@NamedQuery(name = "EuPrixCarte.findAll", query = "SELECT e FROM EuPrixCarte e")
public class EuPrixCarte implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idPrixCarte;
    private String codeCat;
    private Integer embosser;
    private double prixCarte;

    public EuPrixCarte() {
    }

    @Id
    @Column(name = "id_prix_carte", unique = true, nullable = false)
    public Integer getIdPrixCarte() {
        return this.idPrixCarte;
    }

    public void setIdPrixCarte(Integer idPrixCarte) {
        this.idPrixCarte = idPrixCarte;
    }

    @Column(name = "code_cat", length = 40)
    public String getCodeCat() {
        return this.codeCat;
    }

    public void setCodeCat(String codeCat) {
        this.codeCat = codeCat;
    }

    public Integer getEmbosser() {
        return this.embosser;
    }

    public void setEmbosser(Integer embosser) {
        this.embosser = embosser;
    }

    @Column(name = "prix_carte")
    public double getPrixCarte() {
        return this.prixCarte;
    }

    public void setPrixCarte(double prixCarte) {
        this.prixCarte = prixCarte;
    }

}
