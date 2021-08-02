package com.esmc.mcnp.domain.mapper.odd;

import com.esmc.mcnp.domain.dto.org.AgenceDTO;
import com.esmc.mcnp.domain.entity.org.EuAgence;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgenceMapper {
    EuAgence toEuAgence(AgenceDTO agenceDTO);

    AgenceDTO fromEuAgence(EuAgence agence);

    List<AgenceDTO> fromEuAgenceList(List<EuAgence> agences);
}
