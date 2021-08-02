package com.esmc.mcnp.domain.entity.obps;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuAnnulationCommande
 *
 */
@Entity
@Table(name = "eu_annulation_commande")
public class EuAnnulationCommande implements Serializable {

	private Integer id;
	private Integer codeCommande;
	private String idDetail;
	private Double montant;
	private static final long serialVersionUID = 1L;

	public EuAnnulationCommande() {
		super();
	}

	@Id
	@Column(name = "id_annulation_commande")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "code_commande")
	public Integer getCodeCommande() {
		return this.codeCommande;
	}

	public void setCodeCommande(Integer codeCommande) {
		this.codeCommande = codeCommande;
	}

	@Column(name = "id_detail")
	public String getIdDetail() {
		return this.idDetail;
	}

	public void setIdDetail(String idDetail) {
		this.idDetail = idDetail;
	}

	@Column(name = "montant")
	public Double getMontant() {
		return this.montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

}
