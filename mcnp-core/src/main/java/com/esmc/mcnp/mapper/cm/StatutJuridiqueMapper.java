package com.esmc.mcnp.mapper.cm;

import com.esmc.mcnp.dto.cm.StatutJuridique;
import com.esmc.mcnp.model.cm.EuStatutJuridique;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatutJuridiqueMapper {
    EuStatutJuridique toEuStatutJuridique(StatutJuridique statutJuridique);

    StatutJuridique fromEuStatutJuridique(EuStatutJuridique euStatutJuridique);
}
