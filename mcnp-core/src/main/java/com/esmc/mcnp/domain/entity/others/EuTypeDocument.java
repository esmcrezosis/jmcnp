package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_type_document database table.
 *
 */
@Entity
@Table(name = "eu_type_document")
@NamedQuery(name = "EuTypeDocument.findAll", query = "SELECT e FROM EuTypeDocument e")
public class EuTypeDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTypeDocument;
    private String libelleTypeDocument;

    public EuTypeDocument() {
    }

    @Id
    @Column(name = "id_type_document", unique = true, nullable = false)
    public Long getIdTypeDocument() {
        return this.idTypeDocument;
    }

    public void setIdTypeDocument(Long idTypeDocument) {
        this.idTypeDocument = idTypeDocument;
    }

    @Column(name = "libelle_type_document", length = 100)
    public String getLibelleTypeDocument() {
        return this.libelleTypeDocument;
    }

    public void setLibelleTypeDocument(String libelleTypeDocument) {
        this.libelleTypeDocument = libelleTypeDocument;
    }

}
