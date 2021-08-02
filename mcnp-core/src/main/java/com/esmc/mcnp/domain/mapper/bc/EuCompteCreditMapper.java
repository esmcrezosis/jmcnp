package com.esmc.mcnp.domain.mapper.bc;

import java.util.List;

import com.esmc.mcnp.domain.dto.bc.CompteCredit;
import com.esmc.mcnp.domain.entity.cm.EuCompteCredit;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EuCompteCreditMapper {
    
    EuCompteCredit toEntity(CompteCredit compteCredit);

    CompteCredit toDto(EuCompteCredit compteCredit);

    List<CompteCredit> toEntities(List<EuCompteCredit> compteCredits);
}