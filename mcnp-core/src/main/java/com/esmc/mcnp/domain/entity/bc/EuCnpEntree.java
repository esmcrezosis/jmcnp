package com.esmc.mcnp.model.bc;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the eu_cnp_entree database table.
 * 
 */
@Entity
@Table(name="eu_cnp_entree")
@NamedQuery(name="EuCnpEntree.findAll", query="SELECT e FROM EuCnpEntree e")
public class EuCnpEntree implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idCnpEntree;
	private Date dateEntree;
	private double montCnpEntree;
	private String typeCnpEntree;
	private EuCnp euCnp;

	public EuCnpEntree() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_cnp_entree", unique=true, nullable=false)
	public Long getIdCnpEntree() {
		return this.idCnpEntree;
	}

	public void setIdCnpEntree(Long idCnpEntree) {
		this.idCnpEntree = idCnpEntree;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_entree")
	public Date getDateEntree() {
		return this.dateEntree;
	}

	public void setDateEntree(Date dateEntree) {
		this.dateEntree = dateEntree;
	}


	@Column(name="mont_cnp_entree")
	public double getMontCnpEntree() {
		return this.montCnpEntree;
	}

	public void setMontCnpEntree(double montCnpEntree) {
		this.montCnpEntree = montCnpEntree;
	}


	@Column(name="type_cnp_entree", length=180)
	public String getTypeCnpEntree() {
		return this.typeCnpEntree;
	}

	public void setTypeCnpEntree(String typeCnpEntree) {
		this.typeCnpEntree = typeCnpEntree;
	}


	//bi-directional many-to-one association to EuCnp
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_cnp")
	public EuCnp getEuCnp() {
		return this.euCnp;
	}

	public void setEuCnp(EuCnp euCnp) {
		this.euCnp = euCnp;
	}

}