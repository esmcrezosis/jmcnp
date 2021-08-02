package com.esmc.mcnp.domain.entity.pc;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the eu_relevedetail database table.
 *
 */
@Entity
@Table(name = "eu_relevedetail")
@DynamicUpdate
@NamedQuery(name = "EuRelevedetail.findAll", query = "SELECT e FROM EuRelevedetail e")
public class EuRelevedetail implements Serializable {

    private static final long serialVersionUID = 1L;
    private int relevedetailId;
    private int publier;
    private int relevedetailCredit;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date relevedetailDate;
    private int relevedetailMontant;
    private String relevedetailProduit;
    private int relevedetailReleve;

    public EuRelevedetail() {
    }

    @Id
    @Column(name = "relevedetail_id")
    public int getRelevedetailId() {
        return this.relevedetailId;
    }

    public void setRelevedetailId(int relevedetailId) {
        this.relevedetailId = relevedetailId;
    }

    public int getPublier() {
        return this.publier;
    }

    public void setPublier(int publier) {
        this.publier = publier;
    }

    @Column(name = "relevedetail_credit")
    public int getRelevedetailCredit() {
        return this.relevedetailCredit;
    }

    public void setRelevedetailCredit(int relevedetailCredit) {
        this.relevedetailCredit = relevedetailCredit;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "relevedetail_date")
    public Date getRelevedetailDate() {
        return this.relevedetailDate;
    }

    public void setRelevedetailDate(Date relevedetailDate) {
        this.relevedetailDate = relevedetailDate;
    }

    @Column(name = "relevedetail_montant")
    public int getRelevedetailMontant() {
        return this.relevedetailMontant;
    }

    public void setRelevedetailMontant(int relevedetailMontant) {
        this.relevedetailMontant = relevedetailMontant;
    }

    @Column(name = "relevedetail_produit")
    public String getRelevedetailProduit() {
        return this.relevedetailProduit;
    }

    public void setRelevedetailProduit(String relevedetailProduit) {
        this.relevedetailProduit = relevedetailProduit;
    }

    @Column(name = "relevedetail_releve")
    public int getRelevedetailReleve() {
        return this.relevedetailReleve;
    }

    public void setRelevedetailReleve(int relevedetailReleve) {
        this.relevedetailReleve = relevedetailReleve;
    }

}
