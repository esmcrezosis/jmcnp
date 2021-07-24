package com.esmc.mcnp.web.model.echange;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ObtenirBci {
	private String codeMembre;
	private String origine;
	private String type;
	private Double montant;
}
