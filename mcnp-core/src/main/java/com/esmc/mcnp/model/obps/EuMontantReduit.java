package com.esmc.mcnp.model.obps;

import com.esmc.mcnp.model.obps.EuGcp;
import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuMontantReduit
 *
 */
@Entity
@Table(name = "eu_montant_reduit")
public class EuMontantReduit implements Serializable {

	private Integer idMontantReduit;
	private Double montantBanNormal;
	private Double montantBanReduit;
	private EuGcp euGcp;
	private static final long serialVersionUID = 1L;

	public EuMontantReduit() {
		super();
	}

	@Id
	@Column(name = "id_montant_reduit")
	public Integer getIdMontantReduit() {
		return this.idMontantReduit;
	}

	public void setIdMontantReduit(Integer idMontantReduit) {
		this.idMontantReduit = idMontantReduit;
	}

	@Column(name = "montant_ban_normal")
	public Double getMontantBanNormal() {
		return this.montantBanNormal;
	}

	public void setMontantBanNormal(Double montantBanNormal) {
		this.montantBanNormal = montantBanNormal;
	}

	@Column(name = "montant_ban_reduit")
	public Double getMontantBanReduit() {
		return this.montantBanReduit;
	}

	public void setMontantBanReduit(Double montantBanReduit) {
		this.montantBanReduit = montantBanReduit;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_gcp")
	public EuGcp getEuGcp() {
		return this.euGcp;
	}

	public void setEuGcp(EuGcp euGcp) {
		this.euGcp = euGcp;
	}

}
