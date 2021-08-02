package com.esmc.mcnp.model.smcipn;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.esmc.mcnp.model.cm.EuMembre;
import com.esmc.mcnp.model.cm.EuMembreMorale;
import com.esmc.mcnp.model.security.EuUtilisateur;

/**
 * The persistent class for the eu_alerte database table.
 *
 */
@Entity
@Table(name = "eu_alerte")
@NamedQuery(name = "EuAlerte.findAll", query = "SELECT e FROM EuAlerte e")
public class EuAlerte implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAlerte;
    private Date dateAlerte;
    private Date heureAlerte;
    private String libAlerte;
    private String motifAlerte;
    private EuUtilisateur euUtilisateur;
    private EuSmcipn euSmcipn;
    private EuMembre euMembre;
    private EuMembreMorale euMembreMorale1;
    private EuMembreMorale euMembreMorale2;
    private EuMembreMorale euMembreMorale3;

    public EuAlerte() {
    }

    @Id
    @Column(name = "id_alerte", unique = true, nullable = false)
    public Long getIdAlerte() {
        return this.idAlerte;
    }

    public void setIdAlerte(Long idAlerte) {
        this.idAlerte = idAlerte;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_alerte")
    public Date getDateAlerte() {
        return this.dateAlerte;
    }

    public void setDateAlerte(Date dateAlerte) {
        this.dateAlerte = dateAlerte;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "heure_alerte")
    public Date getHeureAlerte() {
        return this.heureAlerte;
    }

    public void setHeureAlerte(Date heureAlerte) {
        this.heureAlerte = heureAlerte;
    }

    @Column(name = "lib_alerte", length = 100)
    public String getLibAlerte() {
        return this.libAlerte;
    }

    public void setLibAlerte(String libAlerte) {
        this.libAlerte = libAlerte;
    }

    @Column(name = "motif_alerte", length = 250)
    public String getMotifAlerte() {
        return this.motifAlerte;
    }

    public void setMotifAlerte(String motifAlerte) {
        this.motifAlerte = motifAlerte;
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

    //bi-directional many-to-one association to EuSmcipn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_smcipn")
    public EuSmcipn getEuSmcipn() {
        return this.euSmcipn;
    }

    public void setEuSmcipn(EuSmcipn euSmcipn) {
        this.euSmcipn = euSmcipn;
    }

    //bi-directional many-to-one association to EuMembre
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre_client")
    public EuMembre getEuMembre() {
        return this.euMembre;
    }

    public void setEuMembre(EuMembre euMembre) {
        this.euMembre = euMembre;
    }

    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre_acteur")
    public EuMembreMorale getEuMembreMorale1() {
        return this.euMembreMorale1;
    }

    public void setEuMembreMorale1(EuMembreMorale euMembreMorale1) {
        this.euMembreMorale1 = euMembreMorale1;
    }

    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre_morale")
    public EuMembreMorale getEuMembreMorale2() {
        return this.euMembreMorale2;
    }

    public void setEuMembreMorale2(EuMembreMorale euMembreMorale2) {
        this.euMembreMorale2 = euMembreMorale2;
    }

    //bi-directional many-to-one association to EuMembreMorale
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_membre_assureur")
    public EuMembreMorale getEuMembreMorale3() {
        return this.euMembreMorale3;
    }

    public void setEuMembreMorale3(EuMembreMorale euMembreMorale3) {
        this.euMembreMorale3 = euMembreMorale3;
    }

}
