package com.esmc.mcnp.mapper.bn;

import java.util.List;

import com.esmc.mcnp.dto.bn.EuCapaDto;
import com.esmc.mcnp.model.ba.EuCapa;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EuCapaMapper {
    
    EuCapa toEntity(EuCapaDto dto);

    EuCapaDto toDto(EuCapa capa);

    List<EuCapaDto> toDtos(List<EuCapa> capas);
}