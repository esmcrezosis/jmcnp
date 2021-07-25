package com.esmc.mcnp.mapper.ksu;

import com.esmc.mcnp.dto.ksu.LivraisonKsu;
import com.esmc.mcnp.model.ksu.EuLivraisonKsu;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LivraisonKsuMapper {
    EuLivraisonKsu toEuLivraisonKsu(LivraisonKsu livraisonKsu);

    LivraisonKsu fromEuLivraisonKsu(EuLivraisonKsu euLivraisonKsu);

    List<LivraisonKsu> fromEuLivraisonKsuList(List<EuLivraisonKsu> euLivraisonKsus);
}
