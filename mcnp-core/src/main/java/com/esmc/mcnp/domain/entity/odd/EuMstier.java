package com.esmc.mcnp.domain.entity.odd;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the eu_mstiers database table.
 *
 */
@Entity
@Table(name = "eu_mstiers")
@NamedQuery(name = "EuMstier.findAll", query = "SELECT e FROM EuMstier e")
public class EuMstier implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idMstiers;
    private String bonNeutreCode;
    private String codeMembre;
    private Date dateMstiers;
    private int idSouscription;
    private double montantRestant;
    private double montantSouscris;
    private double montantUtilise;
    private String typeSouscription;
    private String statutMstiers;
    private String typeMstiers;
    private String typeSouscripteur;

    public EuMstier() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mstiers")
    public int getIdMstiers() {
        return this.idMstiers;
    }

    public void setIdMstiers(int idMstiers) {
        this.idMstiers = idMstiers;
    }

    @Column(name = "bon_neutre_code")
    public String getBonNeutreCode() {
        return this.bonNeutreCode;
    }

    public void setBonNeutreCode(String bonNeutreCode) {
        this.bonNeutreCode = bonNeutreCode;
    }

    @Column(name = "code_membre")
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_mstiers")
    public Date getDateMstiers() {
        return this.dateMstiers;
    }

    public void setDateMstiers(Date dateMstiers) {
        this.dateMstiers = dateMstiers;
    }

    @Column(name = "id_souscription")
    public int getIdSouscription() {
        return this.idSouscription;
    }

    public void setIdSouscription(int idSouscription) {
        this.idSouscription = idSouscription;
    }

    @Column(name = "montant_restant")
    public double getMontantRestant() {
        return this.montantRestant;
    }

    public void setMontantRestant(double montantRestant) {
        this.montantRestant = montantRestant;
    }

    @Column(name = "montant_souscris")
    public double getMontantSouscris() {
        return this.montantSouscris;
    }

    public void setMontantSouscris(double montantSouscris) {
        this.montantSouscris = montantSouscris;
    }

    @Column(name = "montant_utilise")
    public double getMontantUtilise() {
        return this.montantUtilise;
    }

    public void setMontantUtilise(double montantUtilise) {
        this.montantUtilise = montantUtilise;
    }

    @Column(name = "type_souscription")
    public String getTypeSouscription() {
        return this.typeSouscription;
    }

    public void setTypeSouscription(String typeSouscription) {
        this.typeSouscription = typeSouscription;
    }
    
    @Column(name = "statut_mstiers")
    public String getStatutMstiers() {
        return statutMstiers;
    }

    public void setStatutMstiers(String statutMstiers) {
        this.statutMstiers = statutMstiers;
    }

    @Column(name = "type_mstiers")
	public String getTypeMstiers() {
		return typeMstiers;
	}

	public void setTypeMstiers(String typeMstiers) {
		this.typeMstiers = typeMstiers;
	}

	@Column(name = "type_souscripteur")
	public String getTypeSouscripteur() {
		return typeSouscripteur;
	}

	public void setTypeSouscripteur(String typeSouscripteur) {
		this.typeSouscripteur = typeSouscripteur;
	}

}
