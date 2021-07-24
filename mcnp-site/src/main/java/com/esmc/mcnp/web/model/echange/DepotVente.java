package com.esmc.mcnp.web.model.echange;

import lombok.Data;

import java.util.List;

@Data
public class DepotVente {
    private String codeMembre;
    private String type;
    private Double montant;
    private Long[] ids;
}
