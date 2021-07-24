package com.esmc.mcnp.web.dto.bon;

public class BnpParam {
	
	private String typeBnp;
	private String typeProduit;
	private Double montBnp;

	public BnpParam() {
	}

	public String getTypeBnp() {
		return typeBnp;
	}

	public void setTypeBnp(String typeBnp) {
		this.typeBnp = typeBnp;
	}

	public String getTypeProduit() {
		return typeProduit;
	}

	public void setTypeProduit(String typeProduit) {
		this.typeProduit = typeProduit;
	}

	public Double getMontBnp() {
		return montBnp;
	}

	public void setMontBnp(Double montBnp) {
		this.montBnp = montBnp;
	}

}
