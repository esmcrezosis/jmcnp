package com.esmc.mcnp.web.model.desendettement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CalcReleve {
    private Integer idReleve;
    private String codeMembre;
    private String newCodeMembre;
    private String typeRessource;
    private String typeCreance;
    private Double montantRec;
}
