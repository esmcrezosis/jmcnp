package com.esmc.mcnp.domain.entity.cm;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EuTelephone
 *
 */
@Entity
@Table(name = "eu_telephone")
public class EuTelephone implements Serializable {

	private Integer idTelephone;
	private String numeroTelephone;
	private String compagnieTelephone;
	private String codeMembre;
	private static final long serialVersionUID = 1L;

	public EuTelephone() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_telephone")
	public Integer getIdTelephone() {
		return this.idTelephone;
	}

	public void setIdTelephone(Integer idTelephone) {
		this.idTelephone = idTelephone;
	}

	@Column(name = "numero_telephone")
	public String getNumeroTelephone() {
		return this.numeroTelephone;
	}

	public void setNumeroTelephone(String numeroTelephone) {
		this.numeroTelephone = numeroTelephone;
	}

	@Column(name = "compagnie_telephone")
	public String getCompagnieTelephone() {
		return this.compagnieTelephone;
	}

	public void setCompagnieTelephone(String compagnieTelephone) {
		this.compagnieTelephone = compagnieTelephone;
	}

	@Column(name = "code_membre")
	public String getCodeMembre() {
		return this.codeMembre;
	}

	public void setCodeMembre(String codeMembre) {
		this.codeMembre = codeMembre;
	}

}
