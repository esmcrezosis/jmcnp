package com.esmc.mcnp.domain.entity.others;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.domain.entity.acteur.EuLouer;
import com.esmc.mcnp.domain.entity.cm.EuMembre;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;
import com.esmc.mcnp.domain.entity.security.EuUtilisateur;

/**
 * The persistent class for the eu_proprietaire database table.
 *
 */
@Entity
@Table(name = "eu_proprietaire")
@NamedQuery(name = "EuProprietaire.findAll", query = "SELECT e FROM EuProprietaire e")
public class EuProprietaire implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idProprietaire;
    private Date dateDeclaration;
    private Integer nbreMaison;
    private List<EuLouer> euLouers;
    //private List<EuMaison> euMaisons;
    private EuMembreMorale euMembreMorale;
    private EuMembre euMembre;
    private EuUtilisateur euUtilisateur;

    public EuProprietaire() {
    }

    @Id
    @Column(name = "id_proprietaire", unique = true, nullable = false)
    public Long getIdProprietaire() {
        return this.idProprietaire;
    }

    public void setIdProprietaire(Long idProprietaire) {
        this.idProprietaire = idProprietaire;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_declaration")
    public Date getDateDeclaration() {
        return this.dateDeclaration;
    }

    public void setDateDeclaration(Date dateDeclaration) {
        this.dateDeclaration = dateDeclaration;
    }

    @Column(name = "nbre_maison", nullable = false)
    public Integer getNbreMaison() {
        return this.nbreMaison;
    }

    public void setNbreMaison(Integer nbreMaison) {
        this.nbreMaison = nbreMaison;
    }

    //bi-directional many-to-one association to EuLouer
    @OneToMany(mappedBy = "euProprietaire")
    public List<EuLouer> getEuLouers() {
        return this.euLouers;
    }

    public void setEuLouers(List<EuLouer> euLouers) {
        this.euLouers = euLouers;
    }

    public EuLouer addEuLouer(EuLouer euLouer) {
        getEuLouers().add(euLouer);
        euLouer.setEuProprietaire(this);

        return euLouer;
    }

    public EuLouer removeEuLouer(EuLouer euLouer) {
        getEuLouers().remove(euLouer);
        euLouer.setEuProprietaire(null);

        return euLouer;
    }

    /*//bi-directional many-to-one association to EuMaison
    @OneToMany(mappedBy = "euProprietaire")
    public List<EuMaison> getEuMaisons() {
        return this.euMaisons;
    }

    public void setEuMaisons(List<EuMaison> euMaisons) {
        this.euMaisons = euMaisons;
    }

    public EuMaison addEuMaison(EuMaison euMaison) {
        getEuMaisons().add(euMaison);
        euMaison.setEuProprietaire(this);

        return euMaison;
    }

    public EuMaison removeEuMaison(EuMaison euMaison) {
        getEuMaisons().remove(euMaison);
        euMaison.setEuProprietaire(null);

        return euMaison;
    }
*/
    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre_ag")
    public EuMembreMorale getEuMembreMorale() {
        return this.euMembreMorale;
    }

    public void setEuMembreMorale(EuMembreMorale euMembreMorale) {
        this.euMembreMorale = euMembreMorale;
    }

    //bi-directional many-to-one association to EuMembre
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre_pro")
    public EuMembre getEuMembre() {
        return this.euMembre;
    }

    public void setEuMembre(EuMembre euMembre) {
        this.euMembre = euMembre;
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

}
