package com.esmc.mcnp.mapper.smcipn;

import com.esmc.mcnp.dto.smcipn.DetailBudget;
import com.esmc.mcnp.model.smcipn.EuDetailBudget;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetailBudgetMapper {
    EuDetailBudget toEuDetailBudget(DetailBudget detailBudget);

    DetailBudget fromEuDetailBudget(EuDetailBudget euDetailBudget);

    List<DetailBudget> fromEuDetailBudgetList(List<EuDetailBudget> euDetailBudgets);
}
