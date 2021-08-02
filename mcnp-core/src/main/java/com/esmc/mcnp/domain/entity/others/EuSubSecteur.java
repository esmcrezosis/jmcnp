package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.esmc.mcnp.domain.entity.org.EuAgence;
import com.esmc.mcnp.domain.entity.org.EuSecteur;

/**
 * The persistent class for the eu_sub_secteur database table.
 *
 */
@Entity
@Table(name = "eu_sub_secteur")
@NamedQuery(name = "EuSubSecteur.findAll", query = "SELECT e FROM EuSubSecteur e")
public class EuSubSecteur implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idSubSecteur;
    private String nomSubSecteur;
    private EuSecteur euSecteur;
    private EuAgence euAgence;

    public EuSubSecteur() {
    }

    @Id
    @Column(name = "id_sub_secteur", unique = true, nullable = false)
    public Integer getIdSubSecteur() {
        return this.idSubSecteur;
    }

    public void setIdSubSecteur(Integer idSubSecteur) {
        this.idSubSecteur = idSubSecteur;
    }

    @Column(name = "nom_sub_secteur", length = 200)
    public String getNomSubSecteur() {
        return this.nomSubSecteur;
    }

    public void setNomSubSecteur(String nomSubSecteur) {
        this.nomSubSecteur = nomSubSecteur;
    }

    //bi-directional many-to-one association to EuSecteur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_secteur")
    public EuSecteur getEuSecteur() {
        return this.euSecteur;
    }

    public void setEuSecteur(EuSecteur euSecteur) {
        this.euSecteur = euSecteur;
    }

    //bi-directional many-to-one association to EuAgence
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_agence")
    public EuAgence getEuAgence() {
        return this.euAgence;
    }

    public void setEuAgence(EuAgence euAgence) {
        this.euAgence = euAgence;
    }

}
