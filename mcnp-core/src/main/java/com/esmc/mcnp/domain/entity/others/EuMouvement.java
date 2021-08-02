package com.esmc.mcnp.domain.entity.others;

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

/**
 * The persistent class for the eu_mouvement database table.
 *
 */
@Entity
@Table(name = "eu_mouvement")
@NamedQuery(name = "EuMouvement.findAll", query = "SELECT e FROM EuMouvement e")
public class EuMouvement implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idMouvement;
    private Date dateMouvement;
    private String typeMouvement;
    private EuEmploye euEmploye;

    public EuMouvement() {
    }

    @Id
    @Column(name = "id_mouvement", unique = true, nullable = false)
    public Long getIdMouvement() {
        return this.idMouvement;
    }

    public void setIdMouvement(Long idMouvement) {
        this.idMouvement = idMouvement;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_mouvement")
    public Date getDateMouvement() {
        return this.dateMouvement;
    }

    public void setDateMouvement(Date dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    @Column(name = "type_mouvement", length = 20)
    public String getTypeMouvement() {
        return this.typeMouvement;
    }

    public void setTypeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    //bi-directional many-to-one association to EuEmploye
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe")
    public EuEmploye getEuEmploye() {
        return this.euEmploye;
    }

    public void setEuEmploye(EuEmploye euEmploye) {
        this.euEmploye = euEmploye;
    }

}
