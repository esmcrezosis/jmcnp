package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the eu_banque database table.
 *
 */
@Entity
@Table(name = "eu_banque")
@NamedQuery(name = "EuBanque.findAll", query = "SELECT e FROM EuBanque e")
public class EuBanque implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codeBanque;
    private String compteBanque;
    private String ibanBanque;
    private String libelleBanque;
    private String codeMembreMorale;
    private Integer defaut;

    public EuBanque() {
    }

    @Id
    @Column(name = "code_banque", unique = true, nullable = false, length = 40)
    public String getCodeBanque() {
        return this.codeBanque;
    }

    public void setCodeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
    }

    @Column(name = "compte_banque", length = 100)
    public String getCompteBanque() {
        return this.compteBanque;
    }

    public void setCompteBanque(String compteBanque) {
        this.compteBanque = compteBanque;
    }

    @Column(name = "iban_banque", length = 100)
    public String getIbanBanque() {
        return this.ibanBanque;
    }

    public void setIbanBanque(String ibanBanque) {
        this.ibanBanque = ibanBanque;
    }

    @Column(name = "libelle_banque", nullable = false, length = 100)
    public String getLibelleBanque() {
        return this.libelleBanque;
    }

    public void setLibelleBanque(String libelleBanque) {
        this.libelleBanque = libelleBanque;
    }

    @Column(name = "code_membre_morale")
    public String getCodeMembreMorale() {
        return codeMembreMorale;
    }

    public void setCodeMembreMorale(String codeMembreMorale) {
        this.codeMembreMorale = codeMembreMorale;
    }

    @Column(name = "defaut")
    public Integer getDefaut() {
        return defaut;
    }

    public void setDefaut(Integer defaut) {
        this.defaut = defaut;
    }

}
