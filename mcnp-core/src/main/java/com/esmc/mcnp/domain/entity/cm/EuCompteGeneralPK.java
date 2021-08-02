package com.esmc.mcnp.domain.entity.cm;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eu_compte_general database table.
 *
 */
@Embeddable
public class EuCompteGeneralPK implements Serializable {
    //default serial version id, required for serializable classes.

    private static final long serialVersionUID = 1L;
    private String codeCompte;
    private String codeTypeCompte;
    private String service;

    public EuCompteGeneralPK() {
    }

    public EuCompteGeneralPK(String codeCompte, String codeTypeCompte, String service) {
        this.codeCompte = codeCompte;
        this.codeTypeCompte = codeTypeCompte;
        this.service = service;
    }

    @Column(name = "code_compte", unique = true, nullable = false, length = 100)
    public String getCodeCompte() {
        return this.codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    @Column(name = "code_type_compte", insertable = false, updatable = false, unique = true, nullable = false, length = 16)
    public String getCodeTypeCompte() {
        return this.codeTypeCompte;
    }

    public void setCodeTypeCompte(String codeTypeCompte) {
        this.codeTypeCompte = codeTypeCompte;
    }

    @Column(unique = true, nullable = false, length = 180)
    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EuCompteGeneralPK)) {
            return false;
        }
        EuCompteGeneralPK castOther = (EuCompteGeneralPK) other;
        return this.codeCompte.equals(castOther.codeCompte)
                && this.codeTypeCompte.equals(castOther.codeTypeCompte)
                && this.service.equals(castOther.service);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.codeCompte.hashCode();
        hash = hash * prime + this.codeTypeCompte.hashCode();
        hash = hash * prime + this.service.hashCode();

        return hash;
    }
}
