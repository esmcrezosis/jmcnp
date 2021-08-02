package com.esmc.mcnp.domain.entity.obps;

import java.io.Serializable;
import java.lang.Double;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.bc.EuBon;

/**
 * Entity implementation class for Entity: EuBonGcpPrelever
 *
 */
@Entity
@Table(name = "eu_bon_gcp_prelever")
public class EuBonGcpPrelever implements Serializable {

	private EuBonGcpPreleverPK id;
	private Double montantBon;
	private EuBon euBon;
	private Long idTpagcp;
	private EuGcpPrelever euGcpPrelever;
	private static final long serialVersionUID = 1L;

	public EuBonGcpPrelever() {
	}

	@EmbeddedId
	public EuBonGcpPreleverPK getId() {
		return this.id;
	}

	public void setId(EuBonGcpPreleverPK id) {
		this.id = id;
	}

	@Column(name = "montant_bon")
	public Double getMontantBon() {
		return this.montantBon;
	}

	public void setMontantBon(Double montantBon) {
		this.montantBon = montantBon;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bon_id", insertable = false, updatable = false, nullable = false)
	public EuBon getEuBon() {
		return this.euBon;
	}

	public void setEuBon(EuBon euBon) {
		this.euBon = euBon;
	}

	@Column(name = "id_tpagcp")
	public Long getIdTpagcp() {
		return idTpagcp;
	}

	public void setIdTpagcp(Long idTpagcp) {
		this.idTpagcp = idTpagcp;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_gcp_prelever", insertable = false, updatable = false, nullable = false)
	public EuGcpPrelever getEuGcpPrelever() {
		return this.euGcpPrelever;
	}

	public void setEuGcpPrelever(EuGcpPrelever euGcpPrelever) {
		this.euGcpPrelever = euGcpPrelever;
	}

}
