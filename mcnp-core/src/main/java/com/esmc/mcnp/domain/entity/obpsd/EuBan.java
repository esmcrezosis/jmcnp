package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the eu_ban database table.
 * 
 */
@Entity
@Table(name="eu_ban")
@NamedQuery(name="EuBan.findAll", query="SELECT e FROM EuBan e")
public class EuBan implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idBan;
	private String codeMembre;
	private Date dateEmission;
	private BigDecimal montEmis;
	private BigDecimal montVendu;
	private BigDecimal solde;
	private List<EuBanVendu> euBanVendus;

	public EuBan() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_ban")
	public Long getIdBan() {
		return this.idBan;
	}

	public void setIdBan(Long idBan) {
		this.idBan = idBan;
	}


	@Column(name="code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_emission")
	public Date getDateEmission() {
		return this.dateEmission;
	}

	public void setDateEmission(Date dateEmission) {
		this.dateEmission = dateEmission;
	}


	@Column(name="mont_emis")
	public BigDecimal getMontEmis() {
		return this.montEmis;
	}

	public void setMontEmis(BigDecimal montEmis) {
		this.montEmis = montEmis;
	}


	@Column(name="mont_vendu")
	public BigDecimal getMontVendu() {
		return this.montVendu;
	}

	public void setMontVendu(BigDecimal montVendu) {
		this.montVendu = montVendu;
	}


	public BigDecimal getSolde() {
		return this.solde;
	}

	public void setSolde(BigDecimal solde) {
		this.solde = solde;
	}


	//bi-directional many-to-one association to EuBanVendu
	@OneToMany(mappedBy="euBan")
	public List<EuBanVendu> getEuBanVendus() {
		return this.euBanVendus;
	}

	public void setEuBanVendus(List<EuBanVendu> euBanVendus) {
		this.euBanVendus = euBanVendus;
	}

	public EuBanVendu addEuBanVendus(EuBanVendu euBanVendus) {
		getEuBanVendus().add(euBanVendus);
		euBanVendus.setEuBan(this);

		return euBanVendus;
	}

	public EuBanVendu removeEuBanVendus(EuBanVendu euBanVendus) {
		getEuBanVendus().remove(euBanVendus);
		euBanVendus.setEuBan(null);

		return euBanVendus;
	}

}