package com.esmc.mcnp.web.dto.smcipn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RecouvrementDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idReleve;
	private String codeMembre;
	private String newCodeMembre;
	private String typeRessource;
	private String typeCreance;

}
