package com.esmc.mcnp.domain.mapper.obps;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.esmc.mcnp.domain.dto.obps.GcpDto;
import com.esmc.mcnp.domain.entity.obps.EuGcp;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GcpMapper {
	EuGcp toEuGcp(GcpDto gcpDto);

	GcpDto fromEuGcp(EuGcp euGcp);

	List<GcpDto> fromEuGcpList(List<EuGcp> euGcps);
}
