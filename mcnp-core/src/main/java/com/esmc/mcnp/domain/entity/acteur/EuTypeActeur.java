package com.esmc.mcnp.domain.entity.acteur;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the eu_type_acteur database table.
 *
 */
@Entity
@Table(name = "eu_type_acteur")
@NamedQuery(name = "EuTypeActeur.findAll", query = "SELECT e FROM EuTypeActeur e")
public class EuTypeActeur implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idTypeActeur;
    private String libTypeActeur;
    private List<EuActeursCreneaux> euActeursCreneauxs;

    public EuTypeActeur() {
    }

    @Id
    @Column(name = "id_type_acteur", unique = true, nullable = false)
    public Integer getIdTypeActeur() {
        return this.idTypeActeur;
    }

    public void setIdTypeActeur(Integer idTypeActeur) {
        this.idTypeActeur = idTypeActeur;
    }

    @Column(name = "lib_type_acteur", nullable = false, length = 100)
    public String getLibTypeActeur() {
        return this.libTypeActeur;
    }

    public void setLibTypeActeur(String libTypeActeur) {
        this.libTypeActeur = libTypeActeur;
    }

    //bi-directional many-to-one association to EuActeursCreneaux
    @OneToMany(mappedBy = "euTypeActeur")
    public List<EuActeursCreneaux> getEuActeursCreneauxs() {
        return this.euActeursCreneauxs;
    }

    public void setEuActeursCreneauxs(List<EuActeursCreneaux> euActeursCreneauxs) {
        this.euActeursCreneauxs = euActeursCreneauxs;
    }

    public EuActeursCreneaux addEuActeursCreneaux(EuActeursCreneaux euActeursCreneaux) {
        getEuActeursCreneauxs().add(euActeursCreneaux);
        euActeursCreneaux.setEuTypeActeur(this);

        return euActeursCreneaux;
    }

    public EuActeursCreneaux removeEuActeursCreneaux(EuActeursCreneaux euActeursCreneaux) {
        getEuActeursCreneauxs().remove(euActeursCreneaux);
        euActeursCreneaux.setEuTypeActeur(null);

        return euActeursCreneaux;
    }

}
