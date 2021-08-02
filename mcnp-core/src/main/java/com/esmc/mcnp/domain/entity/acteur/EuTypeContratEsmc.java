package com.esmc.mcnp.domain.entity.acteur;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_type_contrat_esmc database table.
 *
 */
@Entity
@Table(name = "eu_type_contrat_esmc")
@NamedQuery(name = "EuTypeContratEsmc.findAll", query = "SELECT e FROM EuTypeContratEsmc e")
public class EuTypeContratEsmc implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idTypecontrat;
    private String codeTypecontrat;
    private String libTypecontrat;

    public EuTypeContratEsmc() {
    }

    @Id
    @Column(name = "id_typecontrat", unique = true, nullable = false)
    public Integer getIdTypecontrat() {
        return this.idTypecontrat;
    }

    public void setIdTypecontrat(Integer idTypecontrat) {
        this.idTypecontrat = idTypecontrat;
    }

    @Column(name = "code_typecontrat", length = 40)
    public String getCodeTypecontrat() {
        return this.codeTypecontrat;
    }

    public void setCodeTypecontrat(String codeTypecontrat) {
        this.codeTypecontrat = codeTypecontrat;
    }

    @Column(name = "lib_typecontrat", length = 100)
    public String getLibTypecontrat() {
        return this.libTypecontrat;
    }

    public void setLibTypecontrat(String libTypecontrat) {
        this.libTypecontrat = libTypecontrat;
    }

}
