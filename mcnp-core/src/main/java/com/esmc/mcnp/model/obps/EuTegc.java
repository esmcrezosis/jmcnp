package com.esmc.mcnp.model.obps;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.esmc.mcnp.model.bc.EuPrk;
import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the eu_tegc database table.
 *
 */
@Entity
@Table(name = "eu_tegc")
@NamedQuery(name = "EuTegc.findAll", query = "SELECT e FROM EuTegc e")
public class EuTegc implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeTegc;
    private Date dateTegc;
    private String nomTegc;
    private String nomProduit;
    private Double montant;
    private Double montantUtilise;
    private Double soldeTegc;
    private String typeTegc;
    private Integer mdv;
    private Integer recurrentLimite;
    private Integer recurrentIllimite;
    private Integer nonrecurrent;
    private Integer periode1;
    private Integer periode2;
    private Integer periode3;
    private Integer ordinaire;
    private Integer special;
    private Integer subvention;
    private Integer regimeTva;
    private Integer formel;
    private Integer tranchePayement;
    private Integer idFiliere;
    private Long idUtilisateur;
    private EuMembreMorale euMembreMorale;
    private EuMembre euMembre;
    private List<EuGcp> euGcps;
    private List<EuGcpPrelever> euGcpPrelevers;
    private List<EuPrk> euPrks;
    /*private Integer idPays;
	private Integer idRegion;
	private Integer idPrefecture;
	private Integer idCanton;*/

    public EuTegc() {
    }

    @Id
    @Column(name = "code_tegc", unique = true, nullable = false)
    public String getCodeTegc() {
        return this.codeTegc;
    }

    public void setCodeTegc(String codeTegc) {
        this.codeTegc = codeTegc;
    }

    @Column(name = "id_filiere", length = 100)
    public Integer getIdFiliere() {
        return this.idFiliere;
    }

    public void setIdFiliere(Integer idFiliere) {
        this.idFiliere = idFiliere;
    }

    public Integer getMdv() {
        return this.mdv;
    }

    public void setMdv(Integer mdv) {
        this.mdv = mdv;
    }

    public Double getMontant() {
        return this.montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    @Column(name = "montant_utilise")
    public Double getMontantUtilise() {
        return this.montantUtilise;
    }

    public void setMontantUtilise(Double montantUtilise) {
        this.montantUtilise = montantUtilise;
    }

    @Column(name = "solde_tegc")
    public Double getSoldeTegc() {
        return this.soldeTegc;
    }

    public void setSoldeTegc(Double soldeTegc) {
        this.soldeTegc = soldeTegc;
    }

    @Column(name = "nonrecurrent")
    public Integer getNonrecurrent() {
        return nonrecurrent;
    }

    public void setNonrecurrent(Integer nonrecurrent) {
        this.nonrecurrent = nonrecurrent;
    }

    @Column(name = "special")
    public Integer getSpecial() {
        return special;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

    @Column(name = "periode1")
    public Integer getPeriode1() {
        return periode1;
    }

    public void setPeriode1(Integer periode1) {
        this.periode1 = periode1;
    }

    @Column(name = "periode2")
    public Integer getPeriode2() {
        return periode2;
    }

    public void setPeriode2(Integer periode2) {
        this.periode2 = periode2;
    }

    @Column(name = "periode3")
    public Integer getPeriode3() {
        return periode3;
    }

    public void setPeriode3(Integer periode3) {
        this.periode3 = periode3;
    }

    @Column(name = "ordinaire")
    public Integer getOrdinaire() {
        return ordinaire;
    }

    public void setOrdinaire(Integer ordinaire) {
        this.ordinaire = ordinaire;
    }

    @Column(name = "recurrent_limite")
    public Integer getRecurrentLimite() {
        return recurrentLimite;
    }

    public void setRecurrentLimite(Integer recurrentLimite) {
        this.recurrentLimite = recurrentLimite;
    }

    @Column(name = "recurrent_illimite")
    public Integer getRecurrentIllimite() {
        return recurrentIllimite;
    }

    public void setRecurrentIllimite(Integer recurrentIllimite) {
        this.recurrentIllimite = recurrentIllimite;
    }

    @Column(name = "subvention")
    public Integer getSubvention() {
        return subvention;
    }

    public void setSubvention(Integer subvention) {
        this.subvention = subvention;
    }

    @Column(name = "nom_tegc")
    public String getNomTegc() {
        return nomTegc;
    }

    public void setNomTegc(String nomTegc) {
        this.nomTegc = nomTegc;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Column(name = "date_tegc")
    public Date getDateTegc() {
        return dateTegc;
    }

    public void setDateTegc(Date dateTegc) {
        this.dateTegc = dateTegc;
    }

    @Column(name = "nom_produit")
    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    @Column(name = "tranche_payement")
    public Integer getTranchePayement() {
        return tranchePayement;
    }

    public void setTranchePayement(Integer tranchePayement) {
        this.tranchePayement = tranchePayement;
    }

    @Column(name = "type_tegc")
    public String getTypeTegc() {
        return typeTegc;
    }

    public void setTypeTegc(String typeTegc) {
        this.typeTegc = typeTegc;
    }

    @Column(name = "regime_tva")
    public Integer getRegimeTva() {
        return regimeTva;
    }

    public void setRegimeTva(Integer regimeTva) {
        this.regimeTva = regimeTva;
    }

    @Column(name = "formel")
    public Integer getFormel() {
        return formel;
    }

    public void setFormel(Integer formel) {
        this.formel = formel;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre_physique")
    public EuMembre getEuMembre() {
        return euMembre;
    }

    public void setEuMembre(EuMembre euMembre) {
        this.euMembre = euMembre;
    }

    // bi-directional many-to-one association to EuMembreMorale
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre")
    public EuMembreMorale getEuMembreMorale() {
        return this.euMembreMorale;
    }

    public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
        this.euMembreMorale = euMembreMorale;
    }

    // bi-directional many-to-one association to EuGcp
    @JsonIgnore
    @OneToMany(mappedBy = "euTegc", fetch = FetchType.LAZY)
    public List<EuGcp> getEuGcps() {
        return this.euGcps;
    }

    public void setEuGcps(List<EuGcp> euGcps) {
        this.euGcps = euGcps;
    }

    public EuGcp addEuGcp(EuGcp euGcp) {
        getEuGcps().add(euGcp);
        euGcp.setEuTegc(this);

        return euGcp;
    }

    public EuGcp removeEuGcp(EuGcp euGcp) {
        getEuGcps().remove(euGcp);
        euGcp.setEuTegc(null);

        return euGcp;
    }

    // bi-directional many-to-one association to EuGcpPrelever
    @JsonIgnore
    @OneToMany(mappedBy = "euTegc", fetch = FetchType.LAZY)
    public List<EuGcpPrelever> getEuGcpPrelevers() {
        return this.euGcpPrelevers;
    }

    public void setEuGcpPrelevers(List<EuGcpPrelever> euGcpPrelevers) {
        this.euGcpPrelevers = euGcpPrelevers;
    }

    public EuGcpPrelever addEuGcpPrelever(EuGcpPrelever euGcpPrelever) {
        getEuGcpPrelevers().add(euGcpPrelever);
        euGcpPrelever.setEuTegc(this);

        return euGcpPrelever;
    }

    public EuGcpPrelever removeEuGcpPrelever(EuGcpPrelever euGcpPrelever) {
        getEuGcpPrelevers().remove(euGcpPrelever);
        euGcpPrelever.setEuTegc(null);

        return euGcpPrelever;
    }

    // bi-directional many-to-one association to EuPrk
    @JsonIgnore
    @OneToMany(mappedBy = "euTegc")
    public List<EuPrk> getEuPrks() {
        return this.euPrks;
    }

    public void setEuPrks(List<EuPrk> euPrks) {
        this.euPrks = euPrks;
    }

    public EuPrk addeuPrks(EuPrk euPrk) {
        getEuPrks().add(euPrk);
        euPrk.setEuTegc(this);

        return euPrk;
    }

    public EuPrk removeEuPrk(EuPrk euPrk) {
        getEuPrks().remove(euPrk);
        euPrk.setEuTegc(null);

        return euPrk;
    }
}
