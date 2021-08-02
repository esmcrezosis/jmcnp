package com.esmc.mcnp.domain.mapper.ot;

import com.esmc.mcnp.domain.dto.ot.EuFormationDto;
import com.esmc.mcnp.domain.entity.ot.EuFormation;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EuFormationMapper {
    EuFormationDto toDto(EuFormation formation);

    EuFormation toEntity(EuFormationDto formationDto);

    List<EuFormationDto> toDtos(List<EuFormation> formations);
}
