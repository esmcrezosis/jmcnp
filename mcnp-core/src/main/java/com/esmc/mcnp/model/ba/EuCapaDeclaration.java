package com.esmc.mcnp.model.ba;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_capa_declaration database table.
 * 
 */
@Entity
@Table(name="eu_capa_declaration")
@NamedQuery(name="EuCapaDeclaration.findAll", query="SELECT e FROM EuCapaDeclaration e")
public class EuCapaDeclaration implements Serializable {
	private static final long serialVersionUID = 1L;
	private double idDeclaration;
	private String codebarMat;
	private String codemembre;
	private Date dateDeclaration;
	private String descriptionMat;
	private String referenceMat;

	public EuCapaDeclaration() {
	}


	@Id
	@Column(name="id_declaration", unique=true, nullable=false)
	public double getIdDeclaration() {
		return this.idDeclaration;
	}

	public void setIdDeclaration(double idDeclaration) {
		this.idDeclaration = idDeclaration;
	}


	@Column(name="codebar_mat", nullable=false, length=200)
	public String getCodebarMat() {
		return this.codebarMat;
	}

	public void setCodebarMat(String codebarMat) {
		this.codebarMat = codebarMat;
	}


	@Column(nullable=false, length=80)
	public String getCodemembre() {
		return this.codemembre;
	}

	public void setCodemembre(String codemembre) {
		this.codemembre = codemembre;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_declaration", nullable=false)
	public Date getDateDeclaration() {
		return this.dateDeclaration;
	}

	public void setDateDeclaration(Date dateDeclaration) {
		this.dateDeclaration = dateDeclaration;
	}


	@Column(name="description_mat", nullable=false, length=120)
	public String getDescriptionMat() {
		return this.descriptionMat;
	}

	public void setDescriptionMat(String descriptionMat) {
		this.descriptionMat = descriptionMat;
	}


	@Column(name="reference_mat", nullable=false, length=120)
	public String getReferenceMat() {
		return this.referenceMat;
	}

	public void setReferenceMat(String referenceMat) {
		this.referenceMat = referenceMat;
	}

}