package com.esmc.mcnp.domain.entity.pc;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eu_cas_passif database table.
 */
@Entity
@Table(name = "eu_cas_passif")
@NamedQuery(name = "EuCasPassif.findAll", query = "SELECT e FROM EuCasPassif e")
public class EuCasPassif implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String entreprise;
    @Lob
    @Column(name = "libelle_cas")
    private String libelleCas;
    @Column(name = "slug_cas")
    private String slugCas;

    public EuCasPassif() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntreprise() {
        return this.entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getLibelleCas() {
        return this.libelleCas;
    }

    public void setLibelleCas(String libelleCas) {
        this.libelleCas = libelleCas;
    }

    public String getSlugCas() {
        return this.slugCas;
    }

    public void setSlugCas(String slugCas) {
        this.slugCas = slugCas;
    }

}