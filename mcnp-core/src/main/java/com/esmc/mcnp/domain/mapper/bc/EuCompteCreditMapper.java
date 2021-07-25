package com.esmc.mcnp.mapper.bc;

import java.util.List;

import com.esmc.mcnp.dto.bc.CompteCredit;
import com.esmc.mcnp.model.cm.EuCompteCredit;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EuCompteCreditMapper {
    
    EuCompteCredit toEntity(CompteCredit compteCredit);

    CompteCredit toDto(EuCompteCredit compteCredit);

    List<CompteCredit> toEntities(List<EuCompteCredit> compteCredits);
}