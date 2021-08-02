package com.esmc.mcnp.domain.entity.cmfh;

import java.io.Serializable;
import javax.persistence.*;

import com.esmc.mcnp.domain.entity.others.EuUnite;

import java.util.Date;


/**
 * The persistent class for the eu_mf_unite database table.
 * 
 */
@Entity
@Table(name="eu_mf_unite")
@NamedQuery(name="EuMfUnite.findAll", query="SELECT e FROM EuMfUnite e")
public class EuMfUnite implements Serializable {
	private static final long serialVersionUID = 1L;
	private EuMfUnitePK id;
	private Date dateMfUnite;
	private EuUnite euUnite;

	public EuMfUnite() {
	}


	@EmbeddedId
	public EuMfUnitePK getId() {
		return this.id;
	}

	public void setId(EuMfUnitePK id) {
		this.id = id;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_mf_unite")
	public Date getDateMfUnite() {
		return this.dateMfUnite;
	}

	public void setDateMfUnite(Date dateMfUnite) {
		this.dateMfUnite = dateMfUnite;
	}


	//bi-directional many-to-one association to EuUnite
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="code_unite", nullable=false, insertable=false, updatable=false)
	public EuUnite getEuUnite() {
		return this.euUnite;
	}

	public void setEuUnite(EuUnite euUnite) {
		this.euUnite = euUnite;
	}

}