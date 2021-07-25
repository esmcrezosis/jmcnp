package com.esmc.mcnp.dto.pbf;

import com.esmc.mcnp.model.obpsd.EuTpagcp;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TraiteDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long traiteId;
    private String traiteCodeBanque;
    private Long traiteTegcp;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date traiteDateDebut;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date traiteDateFin;
    private Integer traiteDisponible;
    private Integer traiteImprimer;
    private String traiteNumero;
    private Integer traitePayer;
    private Double traiteMontant;
    private Integer traiteAvantVte;
    private String traiteStatut;
    private String modePaiement;
    private String referencePaiement;
    private Integer traiter;
    private EuTpagcp tpagcp;
}
