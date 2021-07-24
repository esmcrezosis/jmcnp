package com.esmc.mcnp.web.dto.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Result {

	private Integer resultat;
	private String message;
	private Double value;

	public Result() {
	}

	public Result(Integer resultat, String message) {
		this.resultat = resultat;
		this.message = message;
	}
}
