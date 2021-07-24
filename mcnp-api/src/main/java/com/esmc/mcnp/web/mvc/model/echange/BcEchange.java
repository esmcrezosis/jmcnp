package com.esmc.mcnp.web.mvc.model.echange;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BcEchange {
    private Long id;
    private String codeMembre;
    private String codeMembreDest;
    private String typeBc;
    private String catBon;
    private String typeRecurrent;
    private Double msbc;
    private Double bc;
    private Double duree;
    private Double nbreRenouvel;
    private Double prk;
}
