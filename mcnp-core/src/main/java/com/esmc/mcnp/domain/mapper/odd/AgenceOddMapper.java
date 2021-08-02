package com.esmc.mcnp.domain.mapper.odd;

import com.esmc.mcnp.domain.dto.odd.AgencesOdd;
import com.esmc.mcnp.domain.entity.odd.EuAgencesOdd;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgenceOddMapper {
    @Mapping(source = "id", target = "idAgencesOdd")
    @Mapping(source = "centre", target = "euCentre")
    @Mapping(source = "canton", target = "euCanton")
    @Mapping(source = "centre.id", target = "euCentre.idCentres")
    EuAgencesOdd toEuAgenceOdd(AgencesOdd agencesOdd);

    @Mapping(source = "idAgencesOdd", target = "id")
    @Mapping(source = "euCentre", target = "centre")
    @Mapping(source = "euCanton", target = "canton")
    @Mapping(source = "euCentre.idCentres", target = "centre.id")
    AgencesOdd fromEuAgenceOdd(EuAgencesOdd euAgencesOdd);

    List<AgencesOdd> fromEuAgencesOddList(List<EuAgencesOdd> agencesOdds);
}
