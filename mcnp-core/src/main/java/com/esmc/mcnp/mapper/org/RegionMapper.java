package com.esmc.mcnp.mapper.org;

import com.esmc.mcnp.dto.org.Region;
import com.esmc.mcnp.model.org.EuRegion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegionMapper {
    EuRegion toEuRegion(Region region);

    Region fromEuRegion(EuRegion euRegion);

    List<Region> fromEuRegions(List<EuRegion> euRegions);
}
