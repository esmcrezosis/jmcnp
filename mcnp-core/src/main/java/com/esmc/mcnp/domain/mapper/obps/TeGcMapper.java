package com.esmc.mcnp.domain.mapper.obps;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.esmc.mcnp.domain.dto.obps.TegcDto;
import com.esmc.mcnp.domain.entity.obps.EuTegc;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeGcMapper {

	TegcDto toView(EuTegc entity);

	EuTegc toEntity(TegcDto view);

	List<TegcDto> toViews(List<EuTegc> entities);

	List<EuTegc> toEntities(List<TegcDto> views);
}
