package com.esmc.mcnp.domain.entity.others;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the eu_procuration database table.
 *
 */
@Entity
@Table(name = "eu_procuration")
@NamedQuery(name = "EuProcuration.findAll", query = "SELECT e FROM EuProcuration e")
public class EuProcuration implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idProcuration;
	private String codeMembreMandant;
	private String codeMembreMandataire;
	private Date dateProcuration;
	private Integer activer;

	public EuProcuration() {
	}

	@Id
	@Column(name = "id_procuration", unique = true, nullable = false)
	public Integer getIdProcuration() {
		return this.idProcuration;
	}

	public void setIdProcuration(Integer idProcuration) {
		this.idProcuration = idProcuration;
	}

	@Column(name = "code_membre_mandant", length = 80)
	public String getCodeMembreMandant() {
		return this.codeMembreMandant;
	}

	public void setCodeMembreMandant(String codeMembreMandant) {
		this.codeMembreMandant = codeMembreMandant;
	}

	@Column(name = "code_membre_mandataire", length = 80)
	public String getCodeMembreMandataire() {
		return this.codeMembreMandataire;
	}

	public void setCodeMembreMandataire(String codeMembreMandataire) {
		this.codeMembreMandataire = codeMembreMandataire;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_procuration")
	public Date getDateProcuration() {
		return this.dateProcuration;
	}

	public void setDateProcuration(Date dateProcuration) {
		this.dateProcuration = dateProcuration;
	}

	public Integer getActiver() {
		return activer;
	}

	public void setActiver(Integer activer) {
		this.activer = activer;
	}

}
