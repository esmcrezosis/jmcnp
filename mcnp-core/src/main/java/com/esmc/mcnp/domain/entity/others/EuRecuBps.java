package com.esmc.mcnp.model.others;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the eu_recu_bps database table.
 *
 */
@Entity
@Table(name = "eu_recu_bps")
@NamedQuery(name = "EuRecuBps.findAll", query = "SELECT e FROM EuRecuBps e")
public class EuRecuBps implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long recuBpsId;
    private String recuBpsLibelle;
    private double recuBpsPrk;
    private Integer zppeId;

    public EuRecuBps() {
    }

    @Id
    @Column(name = "recu_bps_id", unique = true, nullable = false)
    public Long getRecuBpsId() {
        return this.recuBpsId;
    }

    public void setRecuBpsId(Long recuBpsId) {
        this.recuBpsId = recuBpsId;
    }

    @Column(name = "recu_bps_libelle", length = 25)
    public String getRecuBpsLibelle() {
        return this.recuBpsLibelle;
    }

    public void setRecuBpsLibelle(String recuBpsLibelle) {
        this.recuBpsLibelle = recuBpsLibelle;
    }

    @Column(name = "recu_bps_prk")
    public double getRecuBpsPrk() {
        return this.recuBpsPrk;
    }

    public void setRecuBpsPrk(double recuBpsPrk) {
        this.recuBpsPrk = recuBpsPrk;
    }

    @Column(name = "zppe_id")
    public Integer getZppeId() {
        return this.zppeId;
    }

    public void setZppeId(Integer zppeId) {
        this.zppeId = zppeId;
    }

}
