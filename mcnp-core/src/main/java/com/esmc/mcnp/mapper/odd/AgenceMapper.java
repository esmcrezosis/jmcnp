package com.esmc.mcnp.mapper.odd;

import com.esmc.mcnp.dto.org.AgenceDTO;
import com.esmc.mcnp.model.org.EuAgence;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgenceMapper {
    EuAgence toEuAgence(AgenceDTO agenceDTO);

    AgenceDTO fromEuAgence(EuAgence agence);

    List<AgenceDTO> fromEuAgenceList(List<EuAgence> agences);
}
