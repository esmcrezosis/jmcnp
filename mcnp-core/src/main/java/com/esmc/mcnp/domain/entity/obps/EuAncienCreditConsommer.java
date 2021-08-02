package com.esmc.mcnp.domain.entity.obps;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by USER on 24/05/2017.
 */
@Entity
@Table(name = "eu_ancien_credit_consommer")
public class EuAncienCreditConsommer implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8368745342611034670L;
	private Long idConsommation;
    private Long idOperation;
    private Long idCredit;
    private String codeMembre;
    private String codeMembreDist;
    private String codeCompte;
    private String codeProduit;
    private double montConsommation;
    private Date dateConsommation;
    private Date heureConsommation;

    @Id
    @Column(name = "id_consommation")
    public Long getIdConsommation() {
        return idConsommation;
    }

    public void setIdConsommation(Long idConsommation) {
        this.idConsommation = idConsommation;
    }

    @Column(name = "id_operation")
    public Long getIdOperation() {
        return idOperation;
    }

    public void setIdOperation(Long idOperation) {
        this.idOperation = idOperation;
    }

    @Column(name = "id_credit")
    public Long getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(Long idCredit) {
        this.idCredit = idCredit;
    }

    @Column(name = "code_membre")
    public String getCodeMembre() {
        return codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(name = "code_membre_dist")
    public String getCodeMembreDist() {
        return codeMembreDist;
    }

    public void setCodeMembreDist(String codeMembreDist) {
        this.codeMembreDist = codeMembreDist;
    }

    @Column(name = "code_compte")
    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    @Column(name = "code_produit")
    public String getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    @Column(name = "mont_consommation")
    public double getMontConsommation() {
        return montConsommation;
    }

    public void setMontConsommation(double montConsommation) {
        this.montConsommation = montConsommation;
    }

    @Column(name = "date_consommation")
    @Temporal(TemporalType.DATE)
    public Date getDateConsommation() {
        return dateConsommation;
    }

    public void setDateConsommation(Date dateConsommation) {
        this.dateConsommation = dateConsommation;
    }

    @Column(name = "heure_consommation")
    @Temporal(TemporalType.TIME)
    public Date getHeureConsommation() {
        return heureConsommation;
    }

    public void setHeureConsommation(Date heureConsommation) {
        this.heureConsommation = heureConsommation;
    }
}
