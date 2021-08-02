package com.esmc.mcnp.domain.mapper.smcipn;

import com.esmc.mcnp.domain.dto.smcipn.Budget;
import com.esmc.mcnp.domain.entity.smcipn.EuBudget;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BudgetMapper {
    EuBudget toEuBudget(Budget budget);

    Budget fromEuBudget(EuBudget euBudget);

    List<Budget> fromEuBudgetList(List<EuBudget> euBudgets);
}
