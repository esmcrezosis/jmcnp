package com.esmc.mcnp.domain.entity.pc;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuOrdrePayement
 *
 */
@Entity
@Table(name = "eu_ordre_payement")
public class EuOrdrePayement implements Serializable {

	private Long idOrdrePayement;
	private Date dateOrdrePayement;
	private EuCreance euCreance;
	private String codeMembreCred;
	private String codeMembreDeb;
	private String codeMembreBenef;
	private int bovAccepter;
	private int dovAccepter;
	private EuCreance dette;
	private static final long serialVersionUID = 1L;

	public EuOrdrePayement() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ordre_payement")
	public Long getIdOrdrePayement() {
		return this.idOrdrePayement;
	}

	public void setIdOrdrePayement(Long idOrdrePayement) {
		this.idOrdrePayement = idOrdrePayement;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_ordre_payement")
	public Date getDateOrdrePayement() {
		return this.dateOrdrePayement;
	}

	public void setDateOrdrePayement(Date dateOrdrePayement) {
		this.dateOrdrePayement = dateOrdrePayement;
	}

	@OneToOne
	@JoinColumn(name = "id_creance")
	public EuCreance getEuCreance() {
		return this.euCreance;
	}

	public void setEuCreance(EuCreance euCreance) {
		this.euCreance = euCreance;
	}

	@Column(name = "code_membre_cred")
	public String getCodeMembreCred() {
		return this.codeMembreCred;
	}

	public void setCodeMembreCred(String codeMembreCred) {
		this.codeMembreCred = codeMembreCred;
	}

	@Column(name = "code_membre_deb")
	public String getCodeMembreDeb() {
		return this.codeMembreDeb;
	}

	public void setCodeMembreDeb(String codeMembreDeb) {
		this.codeMembreDeb = codeMembreDeb;
	}

	@Column(name = "code_membre_benef")
	public String getCodeMembreBenef() {
		return this.codeMembreBenef;
	}

	public void setCodeMembreBenef(String codeMembreBenef) {
		this.codeMembreBenef = codeMembreBenef;
	}

	@Column(name = "bov_accepter")
	public int getBovAccepter() {
		return this.bovAccepter;
	}


	public void setBovAccepter(int bovAccepter) {
		this.bovAccepter = bovAccepter;
	}

	@Column(name = "dov_accepter")
	public int getDovAccepter() {
		return this.dovAccepter;
	}

	public void setDovAccepter(int dovAccepter) {
		this.dovAccepter = dovAccepter;
	}

	@OneToOne
	@JoinColumn(name = "id_dette")
	public EuCreance getDette() {
		return dette;
	}

	public void setDette(EuCreance dette) {
		this.dette = dette;
	}

}
