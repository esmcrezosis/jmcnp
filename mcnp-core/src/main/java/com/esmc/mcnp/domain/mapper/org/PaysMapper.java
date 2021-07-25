package com.esmc.mcnp.mapper.org;

import com.esmc.mcnp.dto.org.Pays;
import com.esmc.mcnp.model.org.EuPays;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaysMapper {
    EuPays toEuPays(Pays pays);

    Pays fromEuPays(EuPays euPays);

    List<Pays> fromEuPaysList(List<EuPays> euPaysList);
}
