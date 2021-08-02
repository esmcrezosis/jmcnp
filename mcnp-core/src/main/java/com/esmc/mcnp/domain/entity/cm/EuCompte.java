package com.esmc.mcnp.domain.entity.cm;

import com.esmc.mcnp.domain.entity.ba.EuCapa;
import com.esmc.mcnp.domain.entity.obpsd.EuEchange;
import com.esmc.mcnp.domain.entity.obpsd.EuEscompte;
import com.esmc.mcnp.domain.entity.obpsd.EuGcpPbf;
import com.esmc.mcnp.domain.entity.obpsd.EuTpagcp;
import com.esmc.mcnp.domain.entity.obpsd.EuTransfertNn;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_compte database table.
 */
@Entity
@Table(name = "eu_compte")
@DynamicUpdate
@NamedQuery(name = "EuCompte.findAll", query = "SELECT e FROM EuCompte e")
public class EuCompte implements Serializable {
    private static final long serialVersionUID = 1L;
    private String codeCompte;
    private String codeTypeCompte;
    private String codeCat;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateAlloc;
    private String libCompte;
    private double solde;
    private String desactiver;
    private String numeroCarte;
    private String mifarecard;
    private String cardprinteddate;
    private Integer cardprintediddate;
    private EuCategorieCompte euCategorieCompte;
    private EuMembre euMembre;
    private EuMembreMorale euMembreMorale;
    private EuTypeCompte euTypeCompte;
    private List<EuCapa> euCapas;
    private List<EuCompteCredit> euCompteCredits;
    private List<EuEchange> euEchanges;
    private List<EuEscompte> euEscomptes;
    private List<EuGcpPbf> euGcpPbfs;
    private List<EuTpagcp> euTpagcps;
    private List<EuTransfertNn> euTransfertNns1;
    private List<EuTransfertNn> euTransfertNns2;

    public EuCompte() {
    }


    @Id
    @Column(name = "code_compte", unique = true, nullable = false, length = 100)
    public String getCodeCompte() {
        return this.codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }


    @Column(length = 255)
    public String getCardprinteddate() {
        return this.cardprinteddate;
    }

    public void setCardprinteddate(String cardprinteddate) {
        this.cardprinteddate = cardprinteddate;
    }


    public Integer getCardprintediddate() {
        return this.cardprintediddate;
    }

    public void setCardprintediddate(Integer cardprintediddate) {
        this.cardprintediddate = cardprintediddate;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_alloc")
    public Date getDateAlloc() {
        return this.dateAlloc;
    }

    public void setDateAlloc(Date dateAlloc) {
        this.dateAlloc = dateAlloc;
    }


    @Column(length = 4)
    public String getDesactiver() {
        return this.desactiver;
    }

    public void setDesactiver(String desactiver) {
        this.desactiver = desactiver;
    }


    @Column(name = "lib_compte", length = 255)
    public String getLibCompte() {
        return this.libCompte;
    }

    public void setLibCompte(String libCompte) {
        this.libCompte = libCompte;
    }


    @Column(length = 255)
    public String getMifarecard() {
        return this.mifarecard;
    }

    public void setMifarecard(String mifarecard) {
        this.mifarecard = mifarecard;
    }


    @Column(name = "numero_carte", length = 255)
    public String getNumeroCarte() {
        return this.numeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }


    public double getSolde() {
        return this.solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    @Column(name = "code_cat", insertable = false, updatable = false)
    public String getCodeCat() {
        return codeCat;
    }

    public void setCodeCat(String codeCat) {
        this.codeCat = codeCat;
    }

    //bi-directional many-to-one association to EuCategorieCompte
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_cat")
    public EuCategorieCompte getEuCategorieCompte() {
        return this.euCategorieCompte;
    }

    public void setEuCategorieCompte(EuCategorieCompte euCategorieCompte) {
        this.euCategorieCompte = euCategorieCompte;
    }


    //bi-directional many-to-one association to EuMembre
    @ManyToOne
    @JoinColumn(name = "code_membre")
    public EuMembre getEuMembre() {
        return this.euMembre;
    }

    public void setEuMembre(EuMembre euMembre) {
        this.euMembre = euMembre;
    }


    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne
    @JoinColumn(name = "code_membre_morale")
    public EuMembreMorale getEuMembreMorale() {
        return this.euMembreMorale;
    }

    public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
        this.euMembreMorale = euMembreMorale;
    }

	@Column(name = "code_type_compte", insertable = false, updatable = false)
	public String getCodeTypeCompte() {
		return codeTypeCompte;
	}

	public void setCodeTypeCompte(String codeTypeCompte) {
		this.codeTypeCompte = codeTypeCompte;
	}

	//bi-directional many-to-one association to EuTypeCompte
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_type_compte")
    public EuTypeCompte getEuTypeCompte() {
        return this.euTypeCompte;
    }

    public void setEuTypeCompte(EuTypeCompte euTypeCompte) {
        this.euTypeCompte = euTypeCompte;
    }


    //bi-directional many-to-one association to EuCapa
    @JsonIgnore
    @OneToMany(mappedBy = "euCompte")
    public List<EuCapa> getEuCapas() {
        return this.euCapas;
    }

    public void setEuCapas(List<EuCapa> euCapas) {
        this.euCapas = euCapas;
    }

    public EuCapa addEuCapa(EuCapa euCapa) {
        getEuCapas().add(euCapa);
        euCapa.setEuCompte(this);

        return euCapa;
    }

    public EuCapa removeEuCapa(EuCapa euCapa) {
        getEuCapas().remove(euCapa);
        euCapa.setEuCompte(null);

        return euCapa;
    }


    //bi-directional many-to-one association to EuCompteCredit
    @JsonIgnore
    @OneToMany(mappedBy = "euCompte")
    public List<EuCompteCredit> getEuCompteCredits() {
        return this.euCompteCredits;
    }

    public void setEuCompteCredits(List<EuCompteCredit> euCompteCredits) {
        this.euCompteCredits = euCompteCredits;
    }

    public EuCompteCredit addEuCompteCredit(EuCompteCredit euCompteCredit) {
        getEuCompteCredits().add(euCompteCredit);
        euCompteCredit.setEuCompte(this);

        return euCompteCredit;
    }

    public EuCompteCredit removeEuCompteCredit(EuCompteCredit euCompteCredit) {
        getEuCompteCredits().remove(euCompteCredit);
        euCompteCredit.setEuCompte(null);

        return euCompteCredit;
    }


    //bi-directional many-to-one association to EuEchange
    @JsonIgnore
    @OneToMany(mappedBy = "euCompte")
    public List<EuEchange> getEuEchanges() {
        return this.euEchanges;
    }

    public void setEuEchanges(List<EuEchange> euEchanges) {
        this.euEchanges = euEchanges;
    }

    public EuEchange addEuEchange(EuEchange euEchange) {
        getEuEchanges().add(euEchange);
        euEchange.setEuCompte(this);

        return euEchange;
    }

    public EuEchange removeEuEchange(EuEchange euEchange) {
        getEuEchanges().remove(euEchange);
        euEchange.setEuCompte(null);

        return euEchange;
    }


    //bi-directional many-to-one association to EuEscompte
    @JsonIgnore
    @OneToMany(mappedBy = "euCompte")
    public List<EuEscompte> getEuEscomptes() {
        return this.euEscomptes;
    }

    public void setEuEscomptes(List<EuEscompte> euEscomptes) {
        this.euEscomptes = euEscomptes;
    }

    public EuEscompte addEuEscompte(EuEscompte euEscompte) {
        getEuEscomptes().add(euEscompte);
        euEscompte.setEuCompte(this);

        return euEscompte;
    }

    public EuEscompte removeEuEscompte(EuEscompte euEscompte) {
        getEuEscomptes().remove(euEscompte);
        euEscompte.setEuCompte(null);

        return euEscompte;
    }


    //bi-directional many-to-one association to EuGcpPbf
    @JsonIgnore
    @OneToMany(mappedBy = "euCompte")
    public List<EuGcpPbf> getEuGcpPbfs() {
        return this.euGcpPbfs;
    }

    public void setEuGcpPbfs(List<EuGcpPbf> euGcpPbfs) {
        this.euGcpPbfs = euGcpPbfs;
    }

    public EuGcpPbf addEuGcpPbf(EuGcpPbf euGcpPbf) {
        getEuGcpPbfs().add(euGcpPbf);
        euGcpPbf.setEuCompte(this);

        return euGcpPbf;
    }

    public EuGcpPbf removeEuGcpPbf(EuGcpPbf euGcpPbf) {
        getEuGcpPbfs().remove(euGcpPbf);
        euGcpPbf.setEuCompte(null);

        return euGcpPbf;
    }


    //bi-directional many-to-one association to EuTpagcp
    @JsonIgnore
    @OneToMany(mappedBy = "euCompte")
    public List<EuTpagcp> getEuTpagcps() {
        return this.euTpagcps;
    }

    public void setEuTpagcps(List<EuTpagcp> euTpagcps) {
        this.euTpagcps = euTpagcps;
    }

    public EuTpagcp addEuTpagcp(EuTpagcp euTpagcp) {
        getEuTpagcps().add(euTpagcp);
        euTpagcp.setEuCompte(this);

        return euTpagcp;
    }

    public EuTpagcp removeEuTpagcp(EuTpagcp euTpagcp) {
        getEuTpagcps().remove(euTpagcp);
        euTpagcp.setEuCompte(null);

        return euTpagcp;
    }


    //bi-directional many-to-one association to EuTransfertNn
    @JsonIgnore
    @OneToMany(mappedBy = "euCompteDist")
    public List<EuTransfertNn> getEuTransfertNns1() {
        return this.euTransfertNns1;
    }

    public void setEuTransfertNns1(List<EuTransfertNn> euTransfertNns1) {
        this.euTransfertNns1 = euTransfertNns1;
    }

    public EuTransfertNn addEuTransfertNns1(EuTransfertNn euTransfertNns1) {
        getEuTransfertNns1().add(euTransfertNns1);
        euTransfertNns1.setEuCompteDist(this);

        return euTransfertNns1;
    }

    public EuTransfertNn removeEuTransfertNns1(EuTransfertNn euTransfertNns1) {
        getEuTransfertNns1().remove(euTransfertNns1);
        euTransfertNns1.setEuCompteDist(null);

        return euTransfertNns1;
    }


    //bi-directional many-to-one association to EuTransfertNn
    @JsonIgnore
    @OneToMany(mappedBy = "euCompteTransfert")
    public List<EuTransfertNn> getEuTransfertNns2() {
        return this.euTransfertNns2;
    }

    public void setEuTransfertNns2(List<EuTransfertNn> euTransfertNns2) {
        this.euTransfertNns2 = euTransfertNns2;
    }

    public EuTransfertNn addEuTransfertNns2(EuTransfertNn euTransfertNns2) {
        getEuTransfertNns2().add(euTransfertNns2);
        euTransfertNns2.setEuCompteTransfert(this);

        return euTransfertNns2;
    }

    public EuTransfertNn removeEuTransfertNns2(EuTransfertNn euTransfertNns2) {
        getEuTransfertNns2().remove(euTransfertNns2);
        euTransfertNns2.setEuCompteTransfert(null);

        return euTransfertNns2;
    }

}