package com.esmc.mcnp.web.dto.security;

import java.io.Serializable;

public class Verif implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idVerif;
	private Integer status;

	public Verif() {
	}

	public Verif(Long idVerif) {
		this.idVerif = idVerif;
	}

	public Verif(Long idVerif, Integer status) {
		this.idVerif = idVerif;
		this.status = status;
	}

	public Long getIdVerif() {
		return idVerif;
	}

	public void setIdVerif(Long idVerif) {
		this.idVerif = idVerif;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
