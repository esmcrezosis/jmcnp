package com.esmc.mcnp.model.obps;

import com.esmc.mcnp.model.bc.EuBon;
import com.esmc.mcnp.model.others.EuOperation;
import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.model.cm.EuCompteCredit;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the eu_credit_consommer database table.
 *
 */
@Entity
@Table(name = "eu_credit_consommer")
@NamedQuery(name = "EuCreditConsommer.findAll", query = "SELECT e FROM EuCreditConsommer e")
public class EuCreditConsommer implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idConsommation;
    private String codeCompte;
    private String codeMembre;
    private String codeMembreMorale;
    private String codeMembreDist;
    private Date dateConsommation;
    private Date heureConsommation;
    private double montConsommation;
    private EuCompteCredit euCompteCredit;
    private EuProduit euProduit;
    private EuOperation euOperation;
    private String codeTypeCredit;
    private String typeProduit;
    private EuBon euBon;

    public EuCreditConsommer() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consommation", unique = true, nullable = false)
    public Long getIdConsommation() {
        return this.idConsommation;
    }

    public void setIdConsommation(Long idConsommation) {
        this.idConsommation = idConsommation;
    }

    @Column(name = "code_compte", length = 180)
    public String getCodeCompte() {
        return this.codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    @Column(name = "code_membre", length = 100)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(name = "code_membre_morale", length = 100)
    public String getCodeMembreMorale() {
        return this.codeMembreMorale;
    }

    public void setCodeMembreMorale(String codeMembreMorale) {
        this.codeMembreMorale = codeMembreMorale;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_consommation")
    public Date getDateConsommation() {
        return this.dateConsommation;
    }

    public void setDateConsommation(Date dateConsommation) {
        this.dateConsommation = dateConsommation;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "heure_consommation")
    public Date getHeureConsommation() {
        return this.heureConsommation;
    }

    public void setHeureConsommation(Date heureConsommation) {
        this.heureConsommation = heureConsommation;
    }

    @Column(name = "mont_consommation")
    public double getMontConsommation() {
        return this.montConsommation;
    }

    public void setMontConsommation(double montConsommation) {
        this.montConsommation = montConsommation;
    }

    //bi-directional many-to-one association to EuCompteCreditTs
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_credit")
    public EuCompteCredit getEuCompteCredit() {
        return this.euCompteCredit;
    }

    public void setEuCompteCredit(EuCompteCredit euCompteCredit) {
        this.euCompteCredit = euCompteCredit;
    }

    //bi-directional many-to-one association to EuProduit
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_produit")
    public EuProduit getEuProduit() {
        return this.euProduit;
    }

    public void setEuProduit(EuProduit euProduit) {
        this.euProduit = euProduit;
    }

    //bi-directional many-to-one association to EuOperation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_operation")
    public EuOperation getEuOperation() {
        return this.euOperation;
    }

    public void setEuOperation(EuOperation euOperation) {
        this.euOperation = euOperation;
    }

    @Column(name = "code_membre_dist")
    public String getCodeMembreDist() {
		return codeMembreDist;
	}

	public void setCodeMembreDist(String codeMembreDist) {
		this.codeMembreDist = codeMembreDist;
	}

    
    //bi-directional many-to-one association to EuBon
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bon_id")
    public EuBon getEuBon() {
        return euBon;
    }
   
	public void setEuBon(EuBon euBon) {
        this.euBon = euBon;
    }

    @Column(name = "code_type_credit")
	public String getCodeTypeCredit() {
		return codeTypeCredit;
	}

	public void setCodeTypeCredit(String codeTypeCredit) {
		this.codeTypeCredit = codeTypeCredit;
	}

	@Column(name = "type_produit")
	public String getTypeProduit() {
		return typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}

}
