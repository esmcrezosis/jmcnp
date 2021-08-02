package com.esmc.mcnp.domain.mapper.org;

import com.esmc.mcnp.domain.dto.org.Region;
import com.esmc.mcnp.domain.entity.org.EuRegion;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegionMapper {
    EuRegion toEuRegion(Region region);

    Region fromEuRegion(EuRegion euRegion);

    List<Region> fromEuRegions(List<EuRegion> euRegions);
}
