package com.esmc.mcnp.mapper.ot;

import com.esmc.mcnp.dto.ot.EuFormationDto;
import com.esmc.mcnp.model.ot.EuFormation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EuFormationMapper {
    EuFormationDto toDto(EuFormation formation);

    EuFormation toEntity(EuFormationDto formationDto);

    List<EuFormationDto> toDtos(List<EuFormation> formations);
}
