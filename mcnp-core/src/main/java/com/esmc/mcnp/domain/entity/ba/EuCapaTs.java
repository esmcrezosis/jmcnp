package com.esmc.mcnp.domain.entity.ba;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.bc.EuBon;
import com.esmc.mcnp.domain.entity.cm.EuCompte;

import java.util.Date;

/**
 * The persistent class for the eu_capa_ts database table.
 *
 */
@Entity
@Table(name = "eu_capa_ts")
@NamedQuery(name = "EuCapaTs.findAll", query = "SELECT e FROM EuCapaTs e")
public class EuCapaTs implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idCapaTs;
    private Date dateCapaTs;
    private Double montant;
    private Double montantSolde;
    private Double montantUtiliser;
    private EuCapa euCapa;
    private EuCompte euCompte;
    private EuBon euBon;

    public EuCapaTs() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_capa_ts")
    public Long getIdCapaTs() {
        return idCapaTs;
    }

    public void setIdCapaTs(Long idCapaTs) {
        this.idCapaTs = idCapaTs;
    }

    @Column(name = "date_capa_ts")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateCapaTs() {
        return dateCapaTs;
    }

    public void setDateCapaTs(Date dateCapaTs) {
        this.dateCapaTs = dateCapaTs;
    }

    public double getMontant() {
        return this.montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    @Column(name = "montant_solde")
    public Double getMontantSolde() {
        return this.montantSolde;
    }

    public void setMontantSolde(Double montantSolde) {
        this.montantSolde = montantSolde;
    }

    @Column(name = "montant_utiliser")
    public Double getMontantUtiliser() {
        return this.montantUtiliser;
    }

    public void setMontantUtiliser(Double montantUtiliser) {
        this.montantUtiliser = montantUtiliser;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_capa")
    public EuCapa getEuCapa() {
        return euCapa;
    }

    public void setEuCapa(EuCapa euCapa) {
        this.euCapa = euCapa;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_compte_ts")
    public EuCompte getEuCompte() {
        return euCompte;
    }

    public void setEuCompte(EuCompte euCompte) {
        this.euCompte = euCompte;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bon_id")
	public EuBon getEuBon() {
		return euBon;
	}

	public void setEuBon(EuBon euBon) {
		this.euBon = euBon;
	}
}
