package com.esmc.mcnp.domain.dto.bn;

import java.io.Serializable;
import java.util.Date;

import com.esmc.mcnp.domain.dto.projections.bc.CompteVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EuCapaDto implements Serializable{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String codeCapa;
    private String codeMembre;
    private String codeProduit;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateCapa;
    private Date heureCapa;
    private Long idOperation;
    private double montantCapa;
    private double montantSolde;
    private double montantUtiliser;
    private String origineCapa;
    private String typeCapa;
    private String etatCapa;
    private CompteVo euCompte;
}