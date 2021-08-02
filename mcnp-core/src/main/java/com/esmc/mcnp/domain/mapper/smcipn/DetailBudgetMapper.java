package com.esmc.mcnp.domain.mapper.smcipn;

import com.esmc.mcnp.domain.dto.smcipn.DetailBudget;
import com.esmc.mcnp.domain.entity.smcipn.EuDetailBudget;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetailBudgetMapper {
    EuDetailBudget toEuDetailBudget(DetailBudget detailBudget);

    DetailBudget fromEuDetailBudget(EuDetailBudget euDetailBudget);

    List<DetailBudget> fromEuDetailBudgetList(List<EuDetailBudget> euDetailBudgets);
}
