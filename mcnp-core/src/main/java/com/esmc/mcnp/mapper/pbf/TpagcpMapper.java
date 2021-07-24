package com.esmc.mcnp.mapper.pbf;

import com.esmc.mcnp.dto.pbf.TpagcpDto;
import com.esmc.mcnp.model.obpsd.EuTpagcp;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TpagcpMapper {
    EuTpagcp toEuTpagcp(TpagcpDto tpagcpDto);

    TpagcpDto fromEuTpagcp(EuTpagcp euTpagcp);

    List<TpagcpDto> fromEuTpagcpList(List<EuTpagcp> euTpagcps);
}
