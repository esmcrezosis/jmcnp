package com.esmc.mcnp.domain.mapper.org;

import com.esmc.mcnp.domain.dto.org.Prefecture;
import com.esmc.mcnp.domain.entity.org.EuPrefecture;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrefectureMapper {
    EuPrefecture toEuPrefecture(Prefecture prefecture);

    Prefecture fromEuPrefecture(EuPrefecture euPrefecture);

    List<Prefecture> fromEuPrefectures(List<EuPrefecture> euPrefectures);
}
