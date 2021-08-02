package com.esmc.mcnp.domain.mapper.cm;

import com.esmc.mcnp.domain.dto.cm.StatutJuridique;
import com.esmc.mcnp.domain.entity.cm.EuStatutJuridique;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatutJuridiqueMapper {
    EuStatutJuridique toEuStatutJuridique(StatutJuridique statutJuridique);

    StatutJuridique fromEuStatutJuridique(EuStatutJuridique euStatutJuridique);
}
