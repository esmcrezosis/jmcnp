package com.esmc.mcnp.web.model.desendettement;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DetailReleve {
    private Integer relevedetailId;
    private Integer relevedetailReleve;
    private String relevedetailProduit;
    private Integer relevedetailCredit;
    private Double relevedetailMontant;
}
