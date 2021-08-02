package com.esmc.mcnp.domain.mapper.org;

import com.esmc.mcnp.domain.dto.org.Zone;
import com.esmc.mcnp.domain.entity.org.EuZone;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ZoneMapper {
    EuZone toEuZone(Zone zone);

    Zone fromEuZone(EuZone euZone);

    List<Zone> fromEuZones(List<EuZone> euZones);
}
