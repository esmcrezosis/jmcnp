package com.esmc.mcnp.dto.projections;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BudgetVO {
    private Long id;
    private String codeBudget;
    private String nomBudget;
}
