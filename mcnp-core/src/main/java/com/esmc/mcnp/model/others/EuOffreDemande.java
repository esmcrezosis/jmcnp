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
 * The persistent class for the eu_offre_demande database table.
 *
 */
@Entity
@Table(name = "eu_offre_demande")
@NamedQuery(name = "EuOffreDemande.findAll", query = "SELECT e FROM EuOffreDemande e")
public class EuOffreDemande implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idOffreDemande;
    private String codeCat;
    private String codeCompte;
    private String codeMembre;
    private Date dateOffreDemande;
    private String idCredit;
    private String numOffreDemande;
    private String typeCreditDe;
    private String typeCreditOf;
    private String typeOffreDemande;

    public EuOffreDemande() {
    }

    @Id
    @Column(name = "id_offre_demande", unique = true, nullable = false)
    public Long getIdOffreDemande() {
        return this.idOffreDemande;
    }

    public void setIdOffreDemande(Long idOffreDemande) {
        this.idOffreDemande = idOffreDemande;
    }

    @Column(name = "code_cat", length = 10)
    public String getCodeCat() {
        return this.codeCat;
    }

    public void setCodeCat(String codeCat) {
        this.codeCat = codeCat;
    }

    @Column(name = "code_compte", length = 125)
    public String getCodeCompte() {
        return this.codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    @Column(name = "code_membre", length = 125)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_offre_demande")
    public Date getDateOffreDemande() {
        return this.dateOffreDemande;
    }

    public void setDateOffreDemande(Date dateOffreDemande) {
        this.dateOffreDemande = dateOffreDemande;
    }

    @Column(name = "id_credit", length = 45)
    public String getIdCredit() {
        return this.idCredit;
    }

    public void setIdCredit(String idCredit) {
        this.idCredit = idCredit;
    }

    @Column(name = "num_offre_demande", length = 300)
    public String getNumOffreDemande() {
        return this.numOffreDemande;
    }

    public void setNumOffreDemande(String numOffreDemande) {
        this.numOffreDemande = numOffreDemande;
    }

    @Column(name = "type_credit_de", length = 250)
    public String getTypeCreditDe() {
        return this.typeCreditDe;
    }

    public void setTypeCreditDe(String typeCreditDe) {
        this.typeCreditDe = typeCreditDe;
    }

    @Column(name = "type_credit_of", length = 255)
    public String getTypeCreditOf() {
        return this.typeCreditOf;
    }

    public void setTypeCreditOf(String typeCreditOf) {
        this.typeCreditOf = typeCreditOf;
    }

    @Column(name = "type_offre_demande", length = 10)
    public String getTypeOffreDemande() {
        return this.typeOffreDemande;
    }

    public void setTypeOffreDemande(String typeOffreDemande) {
        this.typeOffreDemande = typeOffreDemande;
    }

}
