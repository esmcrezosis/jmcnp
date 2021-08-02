package com.esmc.mcnp.domain.dto.obps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Article {
	private String codeMembreMorale;
	private String reference;
    private String designation;
    private Double prix;
}
