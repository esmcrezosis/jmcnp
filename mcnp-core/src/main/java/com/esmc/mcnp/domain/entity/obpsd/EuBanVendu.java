package com.esmc.mcnp.domain.entity.obpsd;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the eu_ban_vendu database table.
 * 
 */
@Entity
@Table(name="eu_ban_vendu")
@NamedQuery(name="EuBanVendu.findAll", query="SELECT e FROM EuBanVendu e")
public class EuBanVendu implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idBanVendu;
	private String codeMembre;
	private Date dateBanVendu;
	private Integer idUser;
	private BigDecimal montVendu;
	private String numeroRecu;
	private String modePaiement;
	private EuBan euBan;

	public EuBanVendu() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_ban_vendu")
	public Long getIdBanVendu() {
		return this.idBanVendu;
	}

	public void setIdBanVendu(Long idBanVendu) {
		this.idBanVendu = idBanVendu;
	}


	@Column(name="code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="date_ban_vendu")
	public Date getDateBanVendu() {
		return this.dateBanVendu;
	}

	public void setDateBanVendu(Date dateBanVendu) {
		this.dateBanVendu = dateBanVendu;
	}


	@Column(name="id_user")
	public Integer getIdUser() {
		return this.idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}


	@Column(name="mont_vendu")
	public BigDecimal getMontVendu() {
		return this.montVendu;
	}

	public void setMontVendu(BigDecimal montVendu) {
		this.montVendu = montVendu;
	}


	@Column(name="numero_recu")
	public String getNumeroRecu() {
		return this.numeroRecu;
	}

	public void setNumeroRecu(String numeroRecu) {
		this.numeroRecu = numeroRecu;
	}


	//bi-directional many-to-one association to EuBan
	@ManyToOne
	@JoinColumn(name="id_ban")
	public EuBan getEuBan() {
		return this.euBan;
	}

	public void setEuBan(EuBan euBan) {
		this.euBan = euBan;
	}

    @Column(name = "mode_paiement")
	public String getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

}