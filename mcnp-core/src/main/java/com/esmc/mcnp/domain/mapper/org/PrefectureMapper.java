package com.esmc.mcnp.mapper.org;

import com.esmc.mcnp.dto.org.Prefecture;
import com.esmc.mcnp.model.org.EuPrefecture;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrefectureMapper {
    EuPrefecture toEuPrefecture(Prefecture prefecture);

    Prefecture fromEuPrefecture(EuPrefecture euPrefecture);

    List<Prefecture> fromEuPrefectures(List<EuPrefecture> euPrefectures);
}
