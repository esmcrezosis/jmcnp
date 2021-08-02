package com.esmc.mcnp.domain.entity.obps;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.cm.EuCategorieCompte;
import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the eu_gcp database table.
 *
 */
@Entity
@Table(name = "eu_gcp")
@NamedQuery(name = "EuGcp.findAll", query = "SELECT e FROM EuGcp e")
public class EuGcp implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeMembre;
    private Date dateConso;
    private Long idGcp;
    private double montGcp;
    private double montPreleve;
    private double reste;
    private Integer idCredit;
    private String source;
    private String typeGcp;
    private EuTegc euTegc;
    private EuMontantReduit euMontantReduit;
    private EuCategorieCompte euCategorieCompte;
    private EuCompteCredit euCompteCredit;
    private EuBon euBon;
    private List<EuGcpPrelever> euGcpPrelevers;

    public EuGcp() {
    }

    @Column(name = "code_membre", length = 100)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_conso")
    public Date getDateConso() {
        return this.dateConso;
    }

    public void setDateConso(Date dateConso) {
        this.dateConso = dateConso;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gcp", nullable = false)
    public Long getIdGcp() {
        return this.idGcp;
    }

    public void setIdGcp(Long idGcp) {
        this.idGcp = idGcp;
    }

    @Column(name = "mont_gcp")
    public double getMontGcp() {
        return this.montGcp;
    }

    public void setMontGcp(double montGcp) {
        this.montGcp = montGcp;
    }

    @Column(name = "mont_preleve")
    public double getMontPreleve() {
        return this.montPreleve;
    }

    public void setMontPreleve(double montPreleve) {
        this.montPreleve = montPreleve;
    }

    public double getReste() {
        return this.reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }

    @Column(name = "id_credit")
    public Integer getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(Integer idCredit) {
        this.idCredit = idCredit;
    }

    @Column(name = "type_gcp")
    public String getTypeGcp() {
        return typeGcp;
    }

    public void setTypeGcp(String typeGcp) {
        this.typeGcp = typeGcp;
    }

    @Column(length = 200)
    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    //bi-directional many-to-one association to EuTegc
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_tegc")
    public EuTegc getEuTegc() {
        return this.euTegc;
    }

    public void setEuTegc(EuTegc euTegc) {
        this.euTegc = euTegc;
    }

    @OneToOne(mappedBy = "euGcp", fetch = FetchType.LAZY)
    public EuMontantReduit getEuMontantReduit() {
		return euMontantReduit;
	}

	public void setEuMontantReduit(EuMontantReduit euMontantReduit) {
		this.euMontantReduit = euMontantReduit;
	}

	//bi-directional many-to-one association to EuCategorieCompte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_cat")
    public EuCategorieCompte getEuCategorieCompte() {
        return this.euCategorieCompte;
    }

    public void setEuCategorieCompte(EuCategorieCompte euCategorieCompte) {
        this.euCategorieCompte = euCategorieCompte;
    }

    //bi-directional many-to-one association to EuCompteCredit
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_credit", insertable = false, updatable = false)
    public EuCompteCredit getEuCompteCredit() {
        return this.euCompteCredit;
    }

    public void setEuCompteCredit(EuCompteCredit euCompteCredit) {
        this.euCompteCredit = euCompteCredit;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bon_id")
    public EuBon getEuBon() {
        return euBon;
    }

    public void setEuBon(EuBon euBon) {
        this.euBon = euBon;
    }

    @OneToMany(mappedBy = "euGcp", fetch = FetchType.LAZY)
    public List<EuGcpPrelever> getEuGcpPrelevers() {
        return euGcpPrelevers;
    }

    public void setEuGcpPrelevers(List<EuGcpPrelever> euGcpPrelevers) {
        this.euGcpPrelevers = euGcpPrelevers;
    }

}
