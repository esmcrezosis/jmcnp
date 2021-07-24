package com.esmc.mcnp.dto.smcipn;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DetailBudget implements Serializable {
    private Long id;
    private String libelleDetailBudget;
    private Double montantInitial;
    private Double montantBudget;
    private String statutDetailBudget;
    private boolean valider;
    private Budget budget;
}
