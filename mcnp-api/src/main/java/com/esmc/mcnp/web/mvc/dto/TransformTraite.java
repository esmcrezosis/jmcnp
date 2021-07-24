package com.esmc.mcnp.web.mvc.dto;

public class TransformTraite {

	private Long idTraite;
	private String mode;

	public TransformTraite() {
		// TODO Auto-generated constructor stub
	}

	public TransformTraite(Long idTraite, String mode) {
		this.idTraite = idTraite;
		this.mode = mode;
	}

	public Long getIdTraite() {
		return idTraite;
	}

	public void setIdTraite(Long idTraite) {
		this.idTraite = idTraite;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
