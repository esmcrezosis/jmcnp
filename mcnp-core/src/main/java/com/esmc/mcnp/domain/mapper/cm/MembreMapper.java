package com.esmc.mcnp.mapper.cm;

import com.esmc.mcnp.dto.cm.Membre;
import com.esmc.mcnp.model.cm.EuMembre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MembreMapper {
    @Mapping(source = "canton", target = "euCanton")
    @Mapping(source = "pays", target = "euPay")
    @Mapping(source = "religion", target = "euReligion")
    @Mapping(source = "agence", target = "euAgence")
    EuMembre toEuMembre(Membre membre);

    @Mapping(source = "euCanton", target = "canton")
    @Mapping(source = "euPay", target = "pays")
    @Mapping(source = "euReligion", target = "religion")
    @Mapping(source = "euAgence", target = "agence")
    Membre fromEuMembre(EuMembre euMembre);

    List<Membre> fromEuMembreList(List<EuMembre> euMembres);
}
