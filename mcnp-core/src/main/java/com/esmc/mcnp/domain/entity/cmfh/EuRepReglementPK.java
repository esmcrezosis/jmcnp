package com.esmc.mcnp.domain.entity.cmfh;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the eu_rep_reglement database table.
 *
 */
@Embeddable
public class EuRepReglementPK implements Serializable {

    //default serial version id, required for serializable classes.

    private static final long serialVersionUID = 1L;
    private Long idRegltMf;
    private Long idRep;

    public EuRepReglementPK() {
    }

    @Column(name = "id_reglt_mf", unique = true, nullable = false)
    public Long getIdRegltMf() {
        return this.idRegltMf;
    }

    public void setIdRegltMf(Long idRegltMf) {
        this.idRegltMf = idRegltMf;
    }

    @Column(name = "id_rep", unique = true, nullable = false)
    public Long getIdRep() {
        return this.idRep;
    }

    public void setIdRep(Long idRep) {
        this.idRep = idRep;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EuRepReglementPK)) {
            return false;
        }
        EuRepReglementPK castOther = (EuRepReglementPK) other;
        return (this.idRegltMf.equals(castOther.idRegltMf))
                && (this.idRep.equals(castOther.idRep));
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.idRegltMf) ^ (java.lang.Double.doubleToLongBits(this.idRegltMf) >>> 32)));
        hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.idRep) ^ (java.lang.Double.doubleToLongBits(this.idRep) >>> 32)));

        return hash;
    }
}
