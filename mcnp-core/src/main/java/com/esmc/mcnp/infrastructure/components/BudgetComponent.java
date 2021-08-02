package com.esmc.mcnp.infrastructure.components;

import org.springframework.stereotype.Component;

import com.esmc.mcnp.domain.entity.smcipn.EuBudget;
import com.esmc.mcnp.infrastructure.services.smcipn.EuBudgetService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuFnService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuSmcService;
import com.esmc.mcnp.infrastructure.services.smcipn.EuSmcipnService;

@Component
public class BudgetComponent {
    private EuBudgetService budgetService;
    private EuSmcipnService smcipnService;
    private EuSmcService smcService;
    private EuFnService fnService;

    public BudgetComponent(EuBudgetService budgetService, EuSmcipnService smcipnService,
                           EuSmcService smcService, EuFnService fnService) {
        this.budgetService = budgetService;
        this.smcipnService = smcipnService;
        this.smcService = smcService;
        this.fnService = fnService;
    }

    public void affecterBudget(EuBudget budget){
        if (budget.isValider()){

        }
    }
}
