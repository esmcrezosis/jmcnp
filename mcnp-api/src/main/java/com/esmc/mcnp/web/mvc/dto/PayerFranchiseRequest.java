package com.esmc.mcnp.web.mvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PayerFranchiseRequest {
	private Integer typeFranchise;
	private String codeMembre;
	private String codeTegc;
	private String categorieBon;
}
