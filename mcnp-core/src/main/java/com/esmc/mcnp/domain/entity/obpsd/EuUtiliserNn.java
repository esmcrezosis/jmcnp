package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_utiliser_nn database table.
 *
 */
@Entity
@Table(name = "eu_utiliser_nn")
@NamedQuery(name = "EuUtiliserNn.findAll", query = "SELECT e FROM EuUtiliserNn e")
public class EuUtiliserNn implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idUtiliserNn;
    private String codeMembreNb;
    private String codeMembreNn;
    private String codeProduit;
    private String codeProduitNn;
    private String codeSms;
    private Date dateTransfert;
    private Long idOperation;
    private Long idUtilisateur;
    private double montTransfert;
    private String numBon;
	private String motif;
	private Long idNn;

    public EuUtiliserNn() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utiliser_nn", unique = true, nullable = false)
    public Long getIdUtiliserNn() {
        return this.idUtiliserNn;
    }

    public void setIdUtiliserNn(Long idUtiliserNn) {
        this.idUtiliserNn = idUtiliserNn;
    }

    @Column(name = "code_membre_nb", length = 180)
    public String getCodeMembreNb() {
        return this.codeMembreNb;
    }

    public void setCodeMembreNb(String codeMembreNb) {
        this.codeMembreNb = codeMembreNb;
    }

    @Column(name = "code_membre_nn", length = 180)
    public String getCodeMembreNn() {
        return this.codeMembreNn;
    }

    public void setCodeMembreNn(String codeMembreNn) {
        this.codeMembreNn = codeMembreNn;
    }

    @Column(name = "code_produit", length = 20)
    public String getCodeProduit() {
        return this.codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    @Column(name = "code_produit_nn", length = 60)
    public String getCodeProduitNn() {
        return this.codeProduitNn;
    }

    public void setCodeProduitNn(String codeProduitNn) {
        this.codeProduitNn = codeProduitNn;
    }

    @Column(name = "code_sms", length = 40)
    public String getCodeSms() {
        return this.codeSms;
    }

    public void setCodeSms(String codeSms) {
        this.codeSms = codeSms;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_transfert")
    public Date getDateTransfert() {
        return this.dateTransfert;
    }

    public void setDateTransfert(Date dateTransfert) {
        this.dateTransfert = dateTransfert;
    }

    @Column(name = "id_operation")
    public Long getIdOperation() {
        return this.idOperation;
    }

    public void setIdOperation(Long idOperation) {
        this.idOperation = idOperation;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "mont_transfert")
    public double getMontTransfert() {
        return this.montTransfert;
    }

    public void setMontTransfert(double montTransfert) {
        this.montTransfert = montTransfert;
    }

    @Column(name = "num_bon", length = 60)
    public String getNumBon() {
        return this.numBon;
    }

    public void setNumBon(String numBon) {
        this.numBon = numBon;
    }

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	@Column(name = "id_nn")
	public Long getIdNn() {
		return idNn;
	}

	public void setIdNn(Long idNn) {
		this.idNn = idNn;
	}

}
