package com.esmc.mcnp.mapper.ksu;

import com.esmc.mcnp.dto.ksu.Carte;
import com.esmc.mcnp.model.ksu.EuCarte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarteMapper {

    @Mapping(source = "codeMembre", target = "euMembre.codeMembre")
    @Mapping(source = "codeMembreMorale", target = "euMembreMorale.codeMembreMorale")
    EuCarte toEuCarte(Carte carte);

    @Mapping(source = "euMembre.codeMembre", target = "codeMembre")
    @Mapping(source = "euMembreMorale.codeMembreMorale", target = "codeMembreMorale")
    Carte fromEuCarte(EuCarte euCarte);

    List<Carte> fromEuCarteList(List<EuCarte> euCartes);
}
