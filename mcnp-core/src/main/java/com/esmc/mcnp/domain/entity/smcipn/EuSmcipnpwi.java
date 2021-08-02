package com.esmc.mcnp.model.smcipn;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.model.pc.EuChargePaye;
import com.esmc.mcnp.model.bc.EuDomiciliation;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the eu_smcipnpwi database table.
 *
 */
@Entity
@Table(name = "eu_smcipnpwi")
@DynamicUpdate
@DynamicInsert
@NamedQuery(name = "EuSmcipnpwi.findAll", query = "SELECT e FROM EuSmcipnpwi e")
public class EuSmcipnpwi implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private List<EuChargePaye> euChargePayeList;

    private static final long serialVersionUID = 1L;
    private String codeSmcipn;
    private String codeMembre;
    private Date dateSmcipn;
    private int etatAllocInvestis;
    private int etatAllocSalaire;
    private Long idUtilisateur;
    private double investisAlloue;
    private double montInvestis;
    private double montSalaire;
    private String numeroAppel;
    private Integer rembourser;
    private double salaireAlloue;
    private String typeNr;
    private String typeSmcipn;
    private List<EuDomiciliation> euDomiciliations;
    private List<EuGcsc> euGcscs;
    private List<EuServir> euServirs;
    private List<EuUtiliser> euUtilisers;

    public EuSmcipnpwi() {
    }

    @Id
    @Column(name = "code_smcipn", unique = true, nullable = false, length = 30)
    public String getCodeSmcipn() {
        return this.codeSmcipn;
    }

    public void setCodeSmcipn(String codeSmcipn) {
        this.codeSmcipn = codeSmcipn;
    }

    @Column(name = "code_membre", length = 25)
    public String getCodeMembre() {
        return this.codeMembre;
    }

    public void setCodeMembre(String codeMembre) {
        this.codeMembre = codeMembre;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_smcipn")
    public Date getDateSmcipn() {
        return this.dateSmcipn;
    }

    public void setDateSmcipn(Date dateSmcipn) {
        this.dateSmcipn = dateSmcipn;
    }

    @Column(name = "etat_alloc_investis")
    public int getEtatAllocInvestis() {
        return etatAllocInvestis;
    }

    public void setEtatAllocInvestis(int etatAllocInvestis) {
        this.etatAllocInvestis = etatAllocInvestis;
    }

    @Column(name = "etat_alloc_salaire")
    public int getEtatAllocSalaire() {
        return etatAllocSalaire;
    }

    public void setEtatAllocSalaire(int etatAllocSalaire) {
        this.etatAllocSalaire = etatAllocSalaire;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "investis_alloue")
    public double getInvestisAlloue() {
        return this.investisAlloue;
    }

    public void setInvestisAlloue(double investisAlloue) {
        this.investisAlloue = investisAlloue;
    }

    @Column(name = "mont_investis")
    public double getMontInvestis() {
        return this.montInvestis;
    }

    public void setMontInvestis(double montInvestis) {
        this.montInvestis = montInvestis;
    }

    @Column(name = "mont_salaire")
    public double getMontSalaire() {
        return this.montSalaire;
    }

    public void setMontSalaire(double montSalaire) {
        this.montSalaire = montSalaire;
    }

    @Column(name = "numero_appel", length = 150)
    public String getNumeroAppel() {
        return this.numeroAppel;
    }

    public void setNumeroAppel(String numeroAppel) {
        this.numeroAppel = numeroAppel;
    }

    public Integer getRembourser() {
        return this.rembourser;
    }

    public void setRembourser(Integer rembourser) {
        this.rembourser = rembourser;
    }

    public void setSalaireAlloue(double salaireAlloue) {
        this.salaireAlloue = salaireAlloue;
    }

    @Column(name = "type_nr", length = 15)
    public String getTypeNr() {
        return this.typeNr;
    }

    public void setTypeNr(String typeNr) {
        this.typeNr = typeNr;
    }

    @Column(name = "type_smcipn", length = 10)
    public String getTypeSmcipn() {
        return this.typeSmcipn;
    }

    public void setTypeSmcipn(String typeSmcipn) {
        this.typeSmcipn = typeSmcipn;
    }

    //bi-directional many-to-one association to EuDomiciliation
    @OneToMany(mappedBy = "euSmcipnpwi", fetch = FetchType.LAZY)
    public List<EuDomiciliation> getEuDomiciliations() {
        return this.euDomiciliations;
    }

    public void setEuDomiciliations(List<EuDomiciliation> euDomiciliations) {
        this.euDomiciliations = euDomiciliations;
    }

    public EuDomiciliation addEuDomiciliation(EuDomiciliation euDomiciliation) {
        getEuDomiciliations().add(euDomiciliation);
        euDomiciliation.setEuSmcipnpwi(this);

        return euDomiciliation;
    }

    public EuDomiciliation removeEuDomiciliation(EuDomiciliation euDomiciliation) {
        getEuDomiciliations().remove(euDomiciliation);
        euDomiciliation.setEuSmcipnpwi(null);

        return euDomiciliation;
    }

    //bi-directional many-to-one association to EuGcsc
    @OneToMany(mappedBy = "euSmcipnpwi", fetch = FetchType.LAZY)
    public List<EuGcsc> getEuGcscs() {
        return this.euGcscs;
    }

    public void setEuGcscs(List<EuGcsc> euGcscs) {
        this.euGcscs = euGcscs;
    }

    public EuGcsc addEuGcsc(EuGcsc euGcsc) {
        getEuGcscs().add(euGcsc);
        euGcsc.setEuSmcipnpwi(this);

        return euGcsc;
    }

    public EuGcsc removeEuGcsc(EuGcsc euGcsc) {
        getEuGcscs().remove(euGcsc);
        euGcsc.setEuSmcipnpwi(null);

        return euGcsc;
    }

    //bi-directional many-to-one association to EuServir
    @OneToMany(mappedBy = "euSmcipnpwi", fetch = FetchType.LAZY)
    public List<EuServir> getEuServirs() {
        return this.euServirs;
    }

    public void setEuServirs(List<EuServir> euServirs) {
        this.euServirs = euServirs;
    }

    public EuServir addEuServir(EuServir euServir) {
        getEuServirs().add(euServir);
        euServir.setEuSmcipnpwi(this);

        return euServir;
    }

    public EuServir removeEuServir(EuServir euServir) {
        getEuServirs().remove(euServir);
        euServir.setEuSmcipnpwi(null);

        return euServir;
    }

    //bi-directional many-to-one association to EuUtiliser
    @OneToMany(mappedBy = "euSmcipnpwi", fetch = FetchType.LAZY)
    public List<EuUtiliser> getEuUtilisers() {
        return this.euUtilisers;
    }

    public void setEuUtilisers(List<EuUtiliser> euUtilisers) {
        this.euUtilisers = euUtilisers;
    }

    public EuUtiliser addEuUtiliser(EuUtiliser euUtiliser) {
        getEuUtilisers().add(euUtiliser);
        euUtiliser.setEuSmcipnpwi(this);

        return euUtiliser;
    }

    public EuUtiliser removeEuUtiliser(EuUtiliser euUtiliser) {
        getEuUtilisers().remove(euUtiliser);
        euUtiliser.setEuSmcipnpwi(null);

        return euUtiliser;
    }

    @Column(name = "salaire_alloue")
    public Double getSalaireAlloue() {
        return salaireAlloue;
    }

    public void setSalaireAlloue(Double salaireAlloue) {
        this.salaireAlloue = salaireAlloue;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "euSmcipnpwi")
    public List<EuChargePaye> getEuChargePayeList() {
        return euChargePayeList;
    }

    public void setEuChargePayeList(List<EuChargePaye> euChargePayeList) {
        this.euChargePayeList = euChargePayeList;
    }

}
