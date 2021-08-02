package com.esmc.mcnp.model.obps;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_codebarre database table.
 * 
 */
@Entity
@Table(name="eu_codebarre")
@NamedQuery(name="EuCodebarre.findAll", query="SELECT e FROM EuCodebarre e")
public class EuCodebarre implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codebarre;
	private String codemembreDem;
	private Date dateGenerer;
	private String typeCodebar;
	private Long idUtilisateur;

	public EuCodebarre() {
	}


	@Id
	@Column(unique=true, nullable=false, length=200)
	public String getCodebarre() {
		return this.codebarre;
	}

	public void setCodebarre(String codebarre) {
		this.codebarre = codebarre;
	}


	@Column(name="codemembre_dem", length=120)
	public String getCodemembreDem() {
		return this.codemembreDem;
	}

	public void setCodemembreDem(String codemembreDem) {
		this.codemembreDem = codemembreDem;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_generer")
	public Date getDateGenerer() {
		return this.dateGenerer;
	}

	public void setDateGenerer(Date dateGenerer) {
		this.dateGenerer = dateGenerer;
	}


	@Column(name="type_codebar", length=8)
	public String getTypeCodebar() {
		return this.typeCodebar;
	}

	public void setTypeCodebar(String typeCodebar) {
		this.typeCodebar = typeCodebar;
	}

	@Column(name="idutilisateur")
	public Long getIdUtilisateur() {
		return idUtilisateur;
	}


	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
	
	

}