package com.esmc.mcnp.dto.obps;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GcpDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idGcp;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateConso;
    private String codeMembre;
    private double montGcp;
    private double montPreleve;
    private double reste;
    private Integer idCredit;
    private String source;
    private String typeGcp;
}
