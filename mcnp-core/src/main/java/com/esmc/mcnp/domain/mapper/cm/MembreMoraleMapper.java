package com.esmc.mcnp.domain.mapper.cm;

import com.esmc.mcnp.domain.dto.cm.MembreMorale;
import com.esmc.mcnp.domain.entity.cm.EuMembreMorale;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MembreMoraleMapper {
	@Mapping(source = "statutJuridique", target = "euStatutJuridique")
	@Mapping(source = "canton", target = "euCanton")
	@Mapping(source = "pays", target = "euPay")
	@Mapping(source = "agence", target = "euAgence")
	EuMembreMorale toEuMembreMorale(MembreMorale membreMorale);

	@Mapping(source = "euStatutJuridique", target = "statutJuridique")
	@Mapping(source = "euCanton", target = "canton")
	@Mapping(source = "euPay", target = "pays")
	@Mapping(source = "euAgence", target = "agence")
	MembreMorale fromEuMembreMorale(EuMembreMorale membreMorale);

	List<MembreMorale> fromEuMembreMorales(List<EuMembreMorale> euMembreMorales);
}
