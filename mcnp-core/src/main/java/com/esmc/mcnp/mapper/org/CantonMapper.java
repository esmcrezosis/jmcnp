package com.esmc.mcnp.mapper.org;

import com.esmc.mcnp.dto.org.Canton;
import com.esmc.mcnp.model.org.EuCanton;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CantonMapper {
    EuCanton toEuCanton(Canton canton);

    Canton fromEuCanton(EuCanton euCanton);

    List<Canton> fromEuCantons(List<EuCanton> euCantons);
}
