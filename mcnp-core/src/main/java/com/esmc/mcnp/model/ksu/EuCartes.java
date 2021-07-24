package com.esmc.mcnp.model.ksu;

import com.esmc.mcnp.model.cm.EuCategorieCompte;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the eu_cartes database table.
 *
 */
@Entity
@Table(name = "eu_cartes")
@NamedQuery(name = "EuCartes.findAll", query = "SELECT e FROM EuCartes e")
public class EuCartes implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idDemande;
    private String cardprinteddate;
    private Integer cardprintediddate;
    private Integer cardprintedidtime;
    private String cardprintedtime;
    private String codeCompte;
    private String codeMembre;
    private Date dateDemande;
    private Date dateLivraison;
    private Long idUtilisateur;
    private Integer imprimer;
    private Integer livrer;
    private double montCarte;
    private EuCategorieCompte euCategorieCompte;

    public EuCartes() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande", unique = true, nullable = false)
    public Long getIdDemande() {
        return this.idDemande;
    }

    public void setIdDemande(Long idDemande) {
        this.idDemande = idDemande;
    }

    @Column(length = 80)
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

    public Integer getCardprintedidtime() {
        return this.cardprintedidtime;
    }

    public void setCardprintedidtime(Integer cardprintedidtime) {
        this.cardprintedidtime = cardprintedidtime;
    }

    @Column(length = 60)
    public String getCardprintedtime() {
        return this.cardprintedtime;
    }

    public void setCardprintedtime(String cardprintedtime) {
        this.cardprintedtime = cardprintedtime;
    }

    @Column(name = "code_compte", length = 200)
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_demande")
    public Date getDateDemande() {
        return this.dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_livraison")
    public Date getDateLivraison() {
        return this.dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    @Column(name = "id_utilisateur")
    public Long getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Integer getImprimer() {
        return this.imprimer;
    }

    public void setImprimer(Integer imprimer) {
        this.imprimer = imprimer;
    }

    public Integer getLivrer() {
        return this.livrer;
    }

    public void setLivrer(Integer livrer) {
        this.livrer = livrer;
    }

    @Column(name = "mont_carte")
    public double getMontCarte() {
        return this.montCarte;
    }

    public void setMontCarte(double montCarte) {
        this.montCarte = montCarte;
    }

    // bi-directional many-to-one association to EuCategorieCompte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_cat")
    public EuCategorieCompte getEuCategorieCompte() {
        return this.euCategorieCompte;
    }

    public void setEuCategorieCompte(EuCategorieCompte euCategorieCompte) {
        this.euCategorieCompte = euCategorieCompte;
    }

}
