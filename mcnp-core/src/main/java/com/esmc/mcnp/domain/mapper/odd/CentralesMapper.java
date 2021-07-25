package com.esmc.mcnp.mapper.odd;

import com.esmc.mcnp.dto.odd.Centrales;
import com.esmc.mcnp.model.odd.EuCentrales;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CentralesMapper {
    @Mapping(source = "id", target = "idCentrales")
    EuCentrales toEuCentrales(Centrales centrales);

    @Mapping(source = "idCentrales", target = "id")
    Centrales fromEuCentrales(EuCentrales centrales);

    List<Centrales> fromEuCentralesList(List<EuCentrales> centralesList);
}
