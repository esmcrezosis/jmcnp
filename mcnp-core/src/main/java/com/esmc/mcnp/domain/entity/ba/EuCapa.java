package com.esmc.mcnp.domain.entity.ba;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.bc.EuCnp;
import com.esmc.mcnp.domain.entity.cm.EuCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompteCreditCapa;
import com.esmc.mcnp.domain.entity.smcipn.EuFn;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the eu_capa database table.
 *
 */
@Entity
@Table(name = "eu_capa")
@NamedQuery(name = "EuCapa.findAll", query = "SELECT e FROM EuCapa e")
public class EuCapa implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeCapa;
    private String codeMembre;
    private String codeProduit;
    private Date dateCapa;
    private String etatCapa;
    private Date heureCapa;
    private Long idOperation;
    private double montantCapa;
    private double montantSolde;
    private double montantUtiliser;
    private String origineCapa;
    private String typeCapa;
    private EuCompte euCompte;
    private List<EuCnp> euCnps;
    private List<EuCompteCreditCapa> euCompteCreditCapas;
    private List<EuFn> euFns;
    private List<EuCapaTs> euCapaTss;

    public EuCapa() {
    }

    @Id
    @Column(name = "code_capa", unique = true, nullable = false, length = 180)
    public String getCodeCapa() {
        return this.codeCapa;
    }

    public void setCodeCapa(String codeCapa) {
        this.codeCapa = codeCapa;
    }

    @Column(name = "code_membre", nullable = false, length = 100)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Column(name = "code_produit", length = 100)
    public String getCodeProduit() {
        return this.codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_capa", nullable = false)
    public Date getDateCapa() {
        return this.dateCapa;
    }

    public void setDateCapa(Date dateCapa) {
        this.dateCapa = dateCapa;
    }

    @Column(name = "etat_capa", length = 180)
    public String getEtatCapa() {
        return this.etatCapa;
    }

    public void setEtatCapa(String etatCapa) {
        this.etatCapa = etatCapa;
    }

    @Temporal(TemporalType.TIME)
    @Column(name = "heure_capa", nullable = false)
    public Date getHeureCapa() {
        return this.heureCapa;
    }

    public void setHeureCapa(Date heureCapa) {
        this.heureCapa = heureCapa;
    }

    @Column(name = "id_operation")
    public Long getIdOperation() {
        return this.idOperation;
    }

    public void setIdOperation(Long idOperation) {
        this.idOperation = idOperation;
    }

    @Column(name = "montant_capa", nullable = false)
    public double getMontantCapa() {
        return this.montantCapa;
    }

    public void setMontantCapa(double montantCapa) {
        this.montantCapa = montantCapa;
    }

    @Column(name = "montant_solde")
    public double getMontantSolde() {
        return this.montantSolde;
    }

    public void setMontantSolde(double montantSolde) {
        this.montantSolde = montantSolde;
    }

    @Column(name = "montant_utiliser")
    public double getMontantUtiliser() {
        return this.montantUtiliser;
    }

    public void setMontantUtiliser(double montantUtiliser) {
        this.montantUtiliser = montantUtiliser;
    }

    @Column(name = "origine_capa", nullable = false, length = 20)
    public String getOrigineCapa() {
        return this.origineCapa;
    }

    public void setOrigineCapa(String origineCapa) {
        this.origineCapa = origineCapa;
    }

    @Column(name = "type_capa", nullable = false, length = 180)
    public String getTypeCapa() {
        return this.typeCapa;
    }

    public void setTypeCapa(String typeCapa) {
        this.typeCapa = typeCapa;
    }

    //bi-directional many-to-one association to EuCompte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_compte")
    public EuCompte getEuCompte() {
        return this.euCompte;
    }

    public void setEuCompte(EuCompte euCompte) {
        this.euCompte = euCompte;
    }

    //bi-directional many-to-one association to EuCnp
    @OneToMany(mappedBy = "euCapa")
    public List<EuCnp> getEuCnps() {
        return this.euCnps;
    }

    public void setEuCnps(List<EuCnp> euCnps) {
        this.euCnps = euCnps;
    }

    public EuCnp addEuCnp(EuCnp euCnp) {
        getEuCnps().add(euCnp);
        euCnp.setEuCapa(this);

        return euCnp;
    }

    public EuCnp removeEuCnp(EuCnp euCnp) {
        getEuCnps().remove(euCnp);
        euCnp.setEuCapa(null);

        return euCnp;
    }

    //bi-directional many-to-one association to EuCompteCreditCapa
    @OneToMany(mappedBy = "euCapa")
    public List<EuCompteCreditCapa> getEuCompteCreditCapas() {
        return this.euCompteCreditCapas;
    }

    public void setEuCompteCreditCapas(List<EuCompteCreditCapa> euCompteCreditCapas) {
        this.euCompteCreditCapas = euCompteCreditCapas;
    }

    public EuCompteCreditCapa addEuCompteCreditCapa(EuCompteCreditCapa euCompteCreditCapa) {
        getEuCompteCreditCapas().add(euCompteCreditCapa);
        euCompteCreditCapa.setEuCapa(this);

        return euCompteCreditCapa;
    }

    public EuCompteCreditCapa removeEuCompteCreditCapa(EuCompteCreditCapa euCompteCreditCapa) {
        getEuCompteCreditCapas().remove(euCompteCreditCapa);
        euCompteCreditCapa.setEuCapa(null);

        return euCompteCreditCapa;
    }

    //bi-directional many-to-one association to EuFn
    @OneToMany(mappedBy = "euCapa")
    public List<EuFn> getEuFns() {
        return this.euFns;
    }

    public void setEuFns(List<EuFn> euFns) {
        this.euFns = euFns;
    }

    public EuFn addEuFn(EuFn euFn) {
        getEuFns().add(euFn);
        euFn.setEuCapa(this);

        return euFn;
    }

    public EuFn removeEuFn(EuFn euFn) {
        getEuFns().remove(euFn);
        euFn.setEuCapa(null);

        return euFn;
    }

    @OneToMany(mappedBy = "euCapa")
	public List<EuCapaTs> getEuCapaTss() {
		return euCapaTss;
	}

	public void setEuCapaTss(List<EuCapaTs> euCapaTss) {
		this.euCapaTss = euCapaTss;
	}

}
