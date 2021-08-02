package com.esmc.mcnp.model.obps;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by USER on 24/05/2017.
 */
@Entity
@Table(name = "eu_ancien_gcp")
public class EuAncienGcp implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idGcp;
    private String codeTegc;
    private String codeCat;
    private String codeMembre;
    private Long idCredit;
    private String source;
    private Date dateConso;
    private double montGcp;
    private double montPreleve;
    private double reste;

    @Id
    @Column(name = "id_gcp")
    public Long getIdGcp() {
        return idGcp;
    }

    public void setIdGcp(Long idGcp) {
        this.idGcp = idGcp;
    }

    @Column(name = "code_tegc")
    public String getCodeTegc() {
        return codeTegc;
    }

    public void setCodeTegc(String codeTegc) {
        this.codeTegc = codeTegc;
    }

    @Column(name = "code_cat")
    public String getCodeCat() {
        return codeCat;
    }

    public void setCodeCat(String codeCat) {
        this.codeCat = codeCat;
    }

    @Column(name = "code_membre")
    public String getCodeMembre() {
        return codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(name = "id_credit")
    public Long getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(Long idCredit) {
        this.idCredit = idCredit;
    }

    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_conso")
    public Date getDateConso() {
        return dateConso;
    }

    public void setDateConso(Date dateConso) {
        this.dateConso = dateConso;
    }

    @Column(name = "mont_gcp")
    public double getMontGcp() {
        return montGcp;
    }

    public void setMontGcp(double montGcp) {
        this.montGcp = montGcp;
    }

    @Column(name = "mont_preleve")
    public double getMontPreleve() {
        return montPreleve;
    }

    public void setMontPreleve(double montPreleve) {
        this.montPreleve = montPreleve;
    }

    @Column(name = "reste")
    public double getReste() {
        return reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }
}
