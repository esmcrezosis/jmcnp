package com.esmc.mcnp.domain.dto.obps;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class TegcDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String codeTegc;
    private Date dateTegc;
    private String nomTegc;
    private String nomProduit;
    private Double montant;
    private Double montantUtilise;
    private Double soldeTegc;
    private String typeTegc;
    private Integer mdv;
    private Integer recurrentLimite;
    private Integer recurrentIllimite;
    private Integer nonrecurrent;
    private Integer periode1;
    private Integer periode2;
    private Integer periode3;
    private Integer ordinaire;
    private Integer special;
    private Integer subvention;
    private Integer regimeTva;
    private Integer formel;
    private Integer tranchePayement;
    private Integer idFiliere;
}
