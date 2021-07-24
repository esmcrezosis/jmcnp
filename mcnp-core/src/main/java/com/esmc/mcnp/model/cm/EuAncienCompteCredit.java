package com.esmc.mcnp.model.cm;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by USER on 24/05/2017.
 */
@Entity
@Table(name = "eu_ancien_compte_credit")
public class EuAncienCompteCredit implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3462135528783956491L;
	private Long idCredit;
    private Long idOperation;
    private String codeMembre;
    private String codeProduit;
    private String codeCompte;
    private double montantPlace;
    private double montantCredit;
    private Date datedeb;
    private Date datefin;
    private String source;
    private Date dateOctroi;
    private String compteSource;
    private String krr;
    private String renouveller;
    private Integer bnp;
    private Integer domicilier;
    private String codeBnp;
    private Integer affecter;
    private String codeTypeCredit;
    private Double prk; 
    private String desactiver;
    private boolean rembourser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_credit")
    public Long getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(Long idCredit) {
        this.idCredit = idCredit;
    }

    @Column(name = "id_operation")
    public Long getIdOperation() {
        return idOperation;
    }

    public void setIdOperation(Long idOperation) {
        this.idOperation = idOperation;
    }

    @Column(name = "code_membre")
    public String getCodeMembre() {
        return codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(name = "code_produit")
    public String getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    @Column(name = "code_compte")
    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    @Column(name = "montant_place")
    public double getMontantPlace() {
        return montantPlace;
    }

    public void setMontantPlace(double montantPlace) {
        this.montantPlace = montantPlace;
    }

    @Column(name = "montant_credit")
    public double getMontantCredit() {
        return montantCredit;
    }

    public void setMontantCredit(double montantCredit) {
        this.montantCredit = montantCredit;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "datedeb")
    public Date getDatedeb() {
        return datedeb;
    }

    public void setDatedeb(Date datedeb) {
        this.datedeb = datedeb;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "datefin")
    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_octroi")
    public Date getDateOctroi() {
        return dateOctroi;
    }

    public void setDateOctroi(Date dateOctroi) {
        this.dateOctroi = dateOctroi;
    }

    @Column(name = "compte_source")
    public String getCompteSource() {
        return compteSource;
    }

    public void setCompteSource(String compteSource) {
        this.compteSource = compteSource;
    }

    @Column(name = "krr")
    public String getKrr() {
        return krr;
    }

    public void setKrr(String krr) {
        this.krr = krr;
    }

    @Column(name = "renouveller")
    public String getRenouveller() {
        return renouveller;
    }

    public void setRenouveller(String renouveller) {
        this.renouveller = renouveller;
    }

    @Column(name = "bnp")
    public Integer getBnp() {
        return bnp;
    }

    public void setBnp(Integer bnp) {
        this.bnp = bnp;
    }

    @Column(name = "domicilier")
    public Integer getDomicilier() {
        return domicilier;
    }

    public void setDomicilier(Integer domicilier) {
        this.domicilier = domicilier;
    }

    @Column(name = "code_bnp")
    public String getCodeBnp() {
        return codeBnp;
    }

    public void setCodeBnp(String codeBnp) {
        this.codeBnp = codeBnp;
    }

    @Column(name = "affecter")
    public Integer getAffecter() {
        return affecter;
    }

    public void setAffecter(Integer affecter) {
        this.affecter = affecter;
    }

    @Column(name = "code_type_credit")
    public String getCodeTypeCredit() {
        return codeTypeCredit;
    }

    public void setCodeTypeCredit(String codeTypeCredit) {
        this.codeTypeCredit = codeTypeCredit;
    }

    @Column(name = "prk")
    public Double getPrk() {
        return prk;
    }

    public void setPrk(Double prk) {
        this.prk = prk;
    }

    @Column(name = "desactiver")
    public String getDesactiver() {
        return desactiver;
    }

    public void setDesactiver(String desactiver) {
        this.desactiver = desactiver;
    }

	public boolean isRembourser() {
		return rembourser;
	}

	public void setRembourser(boolean rembourser) {
		this.rembourser = rembourser;
	}
}
