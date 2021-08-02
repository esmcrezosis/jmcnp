package com.esmc.mcnp.domain.mapper.pc;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.esmc.mcnp.domain.dto.desendettement.ReclamationDto;
import com.esmc.mcnp.domain.entity.pc.EuReclamation;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReclamationMapper {

	@Mapping(source = "idCasPassif", target = "casPassif.id")
	EuReclamation toEntity(ReclamationDto dto);

	@Mapping(source = "casPassif.id", target = "idCasPassif")
	ReclamationDto toDto(EuReclamation reclamation);

	List<EuReclamation> toEntities(List<ReclamationDto> dtos);

	List<ReclamationDto> toDtos(List<EuReclamation> entities);
}
