package com.esmc.mcnp.domain.mapper.pbf;

import com.esmc.mcnp.domain.dto.pbf.TraiteDto;
import com.esmc.mcnp.domain.entity.obpsd.EuTraite;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TraiteMapper {
    EuTraite toEuTraite(TraiteDto traiteDto);

    TraiteDto fromEuTraite(EuTraite euTraite);

    List<TraiteDto> fromEuTraiteList(List<EuTraite> euTraites);
}
