package com.esmc.mcnp.domain.entity.obps;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the eu_detail_tpagcp database table.
 * 
 */
@Entity
@Table(name = "eu_detail_tpagcp")
@NamedQuery(name = "EuDetailTpagcp.findAll", query = "SELECT e FROM EuDetailTpagcp e")
public class EuDetailTpagcp implements Serializable {
	private static final long serialVersionUID = 1L;
	private EuDetailTpagcpPK id;
	private double montant;

	public EuDetailTpagcp() {
	}

	@EmbeddedId
	public EuDetailTpagcpPK getId() {
		return this.id;
	}

	public void setId(EuDetailTpagcpPK id) {
		this.id = id;
	}

	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

}