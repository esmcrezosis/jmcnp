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

import com.esmc.mcnp.domain.entity.security.EuUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the eu_region database table.
 *
 */
@Entity
@Table(name = "eu_region")
@NamedQuery(name = "EuRegion.findAll", query = "SELECT e FROM EuRegion e")
public class EuRegion implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idRegion;
    private String nomRegion;
    @JsonIgnore
    private EuUtilisateur euUtilisateur;
    @JsonIgnore
    private EuPays euPay;
    @JsonIgnore
    private List<EuSecteur> euSecteurs;

    public EuRegion() {
    }

    @Id
    @Column(name = "id_region", unique = true, nullable = false)
    public Integer getIdRegion() {
        return this.idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    @Column(name = "nom_region", length = 150)
    public String getNomRegion() {
        return this.nomRegion;
    }

    public void setNomRegion(String nomRegion) {
        this.nomRegion = nomRegion;
    }

    //bi-directional many-to-one association to EuUtilisateur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    public EuUtilisateur getEuUtilisateur() {
        return this.euUtilisateur;
    }

    public void setEuUtilisateur(EuUtilisateur euUtilisateur) {
        this.euUtilisateur = euUtilisateur;
    }

    //bi-directional many-to-one association to EuPays
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pays")
    public EuPays getEuPay() {
        return this.euPay;
    }

    public void setEuPay(EuPays euPay) {
        this.euPay = euPay;
    }

    //bi-directional many-to-one association to EuSecteur
    @OneToMany(mappedBy = "euRegion")
    public List<EuSecteur> getEuSecteurs() {
        return this.euSecteurs;
    }

    public void setEuSecteurs(List<EuSecteur> euSecteurs) {
        this.euSecteurs = euSecteurs;
    }

    public EuSecteur addEuSecteur(EuSecteur euSecteur) {
        getEuSecteurs().add(euSecteur);
        euSecteur.setEuRegion(this);

        return euSecteur;
    }

    public EuSecteur removeEuSecteur(EuSecteur euSecteur) {
        getEuSecteurs().remove(euSecteur);
        euSecteur.setEuRegion(null);

        return euSecteur;
    }

}
