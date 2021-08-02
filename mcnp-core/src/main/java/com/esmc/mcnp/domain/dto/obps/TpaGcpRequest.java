package com.esmc.mcnp.domain.dto.obps;

import java.util.List;

public class TpaGcpRequest {

	private Long idTpagcp;
	private Integer nbre;
	private List<Long> idTraites;

	public TpaGcpRequest() {
	}

	public TpaGcpRequest(Long idTpagcp, Integer nbre) {
		this.idTpagcp = idTpagcp;
		this.nbre = nbre;
	}

	public TpaGcpRequest(Long idTpagcp2, Integer nbreOpi, List<Long> traites) {
		this.idTpagcp = idTpagcp2;
		this.nbre = nbreOpi;
		this.idTraites = traites;
	}

	public Long getIdTpagcp() {
		return idTpagcp;
	}

	public void setIdTpagcp(Long idTpagcp) {
		this.idTpagcp = idTpagcp;
	}

	public Integer getNbre() {
		return nbre;
	}

	public void setNbre(Integer nbre) {
		this.nbre = nbre;
	}

	public List<Long> getIdTraites() {
		return idTraites;
	}

	public void setIdTraites(List<Long> idTraites) {
		this.idTraites = idTraites;
	}

}
