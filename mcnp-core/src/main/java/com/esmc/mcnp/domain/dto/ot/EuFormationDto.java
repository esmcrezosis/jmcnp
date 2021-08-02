package com.esmc.mcnp.domain.dto.ot;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EuFormationDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String codeBci;
    private String codeMembre;
    private String libelleFormation;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateBci;
    private Boolean activer;
}
