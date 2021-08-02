package com.esmc.mcnp.domain.entity.org;

import java.io.Serializable;
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

import com.esmc.mcnp.domain.entity.acteur.EuContrat;
import com.esmc.mcnp.domain.entity.cm.EuAncienMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.others.EuTaxe;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the eu_pays database table.
 *
 */
@Entity
@Table(name = "eu_pays")
@NamedQuery(name = "EuPays.findAll", query = "SELECT e FROM EuPays e")
public class EuPays implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idPays;
    private String codePays;
    private String codeTelephonique;
    private String libellePays;
    private String nationalite;
    @JsonIgnore
    private List<EuAncienMembre> euAncienMembres;
    @JsonIgnore
    private List<EuContrat> euContrats;
    @JsonIgnore
    private List<EuMembre> euMembres;
    @JsonIgnore
    private List<EuMembreMorale> euMembreMorales;
    @JsonIgnore
    private EuZone euZone;
    @JsonIgnore
    private List<EuRegion> euRegions;
    @JsonIgnore
    private List<EuSecteur> euSecteurs;
    @JsonIgnore
    private List<EuTaxe> euTaxes;
    @JsonIgnore
    private List<EuVille> euVilles;

    public EuPays() {
    }

    @Id
    @Column(name = "id_pays", unique = true, nullable = false)
    public Integer getIdPays() {
        return this.idPays;
    }

    public void setIdPays(Integer idPays) {
        this.idPays = idPays;
    }

    @Column(name = "code_pays", nullable = false, length = 40)
    public String getCodePays() {
        return this.codePays;
    }

    public void setCodePays(String codePays) {
        this.codePays = codePays;
    }

    @Column(name = "code_telephonique", length = 80)
    public String getCodeTelephonique() {
        return this.codeTelephonique;
    }

    public void setCodeTelephonique(String codeTelephonique) {
        this.codeTelephonique = codeTelephonique;
    }

    @Column(name = "libelle_pays", nullable = false, length = 200)
    public String getLibellePays() {
        return this.libellePays;
    }

    public void setLibellePays(String libellePays) {
        this.libellePays = libellePays;
    }

    @Column(nullable = false, length = 150)
    public String getNationalite() {
        return this.nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    //bi-directional many-to-one association to EuAncienMembre
    @JsonIgnore
    @OneToMany(mappedBy = "euPay")
    public List<EuAncienMembre> getEuAncienMembres() {
        return this.euAncienMembres;
    }

    public void setEuAncienMembres(List<EuAncienMembre> euAncienMembres) {
        this.euAncienMembres = euAncienMembres;
    }

    public EuAncienMembre addEuAncienMembre(EuAncienMembre euAncienMembre) {
        getEuAncienMembres().add(euAncienMembre);
        euAncienMembre.setEuPay(this);

        return euAncienMembre;
    }

    public EuAncienMembre removeEuAncienMembre(EuAncienMembre euAncienMembre) {
        getEuAncienMembres().remove(euAncienMembre);
        euAncienMembre.setEuPay(null);

        return euAncienMembre;
    }

    //bi-directional many-to-one association to EuContrat
    @JsonIgnore
    @OneToMany(mappedBy = "euPay")
    public List<EuContrat> getEuContrats() {
        return this.euContrats;
    }

    public void setEuContrats(List<EuContrat> euContrats) {
        this.euContrats = euContrats;
    }

    public EuContrat addEuContrat(EuContrat euContrat) {
        getEuContrats().add(euContrat);
        euContrat.setEuPay(this);

        return euContrat;
    }

    public EuContrat removeEuContrat(EuContrat euContrat) {
        getEuContrats().remove(euContrat);
        euContrat.setEuPay(null);

        return euContrat;
    }

    //bi-directional many-to-one association to EuMembre
    @JsonIgnore
    @OneToMany(mappedBy = "euPay")
    public List<EuMembre> getEuMembres() {
        return this.euMembres;
    }

    public void setEuMembres(List<EuMembre> euMembres) {
        this.euMembres = euMembres;
    }

    public EuMembre addEuMembre(EuMembre euMembre) {
        getEuMembres().add(euMembre);
        euMembre.setEuPay(this);

        return euMembre;
    }

    public EuMembre removeEuMembre(EuMembre euMembre) {
        getEuMembres().remove(euMembre);
        euMembre.setEuPay(null);

        return euMembre;
    }

    //bi-directional many-to-one association to EuMembreMorale
    @JsonIgnore
    @OneToMany(mappedBy = "euPay")
    public List<EuMembreMorale> getEuMembreMorales() {
        return this.euMembreMorales;
    }

    public void setEuMembreMorales(List<EuMembreMorale> euMembreMorales) {
        this.euMembreMorales = euMembreMorales;
    }

    public EuMembreMorale addEuMembreMorale(EuMembreMorale euMembreMorale) {
        getEuMembreMorales().add(euMembreMorale);
        euMembreMorale.setEuPay(this);

        return euMembreMorale;
    }

    public EuMembreMorale removeEuMembreMorale(EuMembreMorale euMembreMorale) {
        getEuMembreMorales().remove(euMembreMorale);
        euMembreMorale.setEuPay(null);

        return euMembreMorale;
    }

    //bi-directional many-to-one association to EuZone
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_zone")
    public EuZone getEuZone() {
        return this.euZone;
    }

    public void setEuZone(EuZone euZone) {
        this.euZone = euZone;
    }

    //bi-directional many-to-one association to EuRegion
    @JsonIgnore
    @OneToMany(mappedBy = "euPay")
    public List<EuRegion> getEuRegions() {
        return this.euRegions;
    }

    public void setEuRegions(List<EuRegion> euRegions) {
        this.euRegions = euRegions;
    }

    public EuRegion addEuRegion(EuRegion euRegion) {
        getEuRegions().add(euRegion);
        euRegion.setEuPay(this);

        return euRegion;
    }

    public EuRegion removeEuRegion(EuRegion euRegion) {
        getEuRegions().remove(euRegion);
        euRegion.setEuPay(null);

        return euRegion;
    }

    //bi-directional many-to-one association to EuSecteur
    @JsonIgnore
    @OneToMany(mappedBy = "euPay")
    public List<EuSecteur> getEuSecteurs() {
        return this.euSecteurs;
    }

    public void setEuSecteurs(List<EuSecteur> euSecteurs) {
        this.euSecteurs = euSecteurs;
    }

    public EuSecteur addEuSecteur(EuSecteur euSecteur) {
        getEuSecteurs().add(euSecteur);
        euSecteur.setEuPay(this);

        return euSecteur;
    }

    public EuSecteur removeEuSecteur(EuSecteur euSecteur) {
        getEuSecteurs().remove(euSecteur);
        euSecteur.setEuPay(null);

        return euSecteur;
    }

    //bi-directional many-to-one association to EuTaxe
    @JsonIgnore
    @OneToMany(mappedBy = "euPay")
    public List<EuTaxe> getEuTaxes() {
        return this.euTaxes;
    }

    public void setEuTaxes(List<EuTaxe> euTaxes) {
        this.euTaxes = euTaxes;
    }

    public EuTaxe addEuTaxe(EuTaxe euTaxe) {
        getEuTaxes().add(euTaxe);
        euTaxe.setEuPay(this);

        return euTaxe;
    }

    public EuTaxe removeEuTaxe(EuTaxe euTaxe) {
        getEuTaxes().remove(euTaxe);
        euTaxe.setEuPay(null);

        return euTaxe;
    }

    //bi-directional many-to-one association to EuVille
    @JsonIgnore
    @OneToMany(mappedBy = "euPay")
    public List<EuVille> getEuVilles() {
        return this.euVilles;
    }

    public void setEuVilles(List<EuVille> euVilles) {
        this.euVilles = euVilles;
    }

    public EuVille addEuVille(EuVille euVille) {
        getEuVilles().add(euVille);
        euVille.setEuPay(this);

        return euVille;
    }

    public EuVille removeEuVille(EuVille euVille) {
        getEuVilles().remove(euVille);
        euVille.setEuPay(null);

        return euVille;
    }

}
