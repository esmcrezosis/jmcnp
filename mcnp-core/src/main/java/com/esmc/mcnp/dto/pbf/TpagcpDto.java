package com.esmc.mcnp.dto.pbf;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class TpagcpDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idTpagcp;
    private String codeMembre;
    private double montGcp;
    private Double montGcpMaj;
    private double montTranche;
    private double montEchu;
    private Integer ntf;
    private Integer resteNtf;
    private double solde;
    private Integer periode;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateDeb;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateDebTranche;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateFin;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateFinTranche;
    private String modeReglement;
    private String typeRessource;
}
