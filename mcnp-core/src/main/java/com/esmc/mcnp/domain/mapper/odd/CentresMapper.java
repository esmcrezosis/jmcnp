package com.esmc.mcnp.mapper.odd;

import com.esmc.mcnp.dto.odd.Centres;
import com.esmc.mcnp.model.odd.EuCentres;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CentresMapper {

    @Mapping(source = "id", target = "idCentres")
    EuCentres toEuCentres(Centres centres);

    @Mapping(source = "idCentres", target = "id")
    Centres fromEuCentres(EuCentres centres);

    List<Centres> fromEuCentresList(List<EuCentres> centresList);
}
