package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import com.esmc.mcnp.domain.entity.obps.EuGcpPrelever;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuGcpPreleverService extends BaseService<EuGcpPrelever, Long> {
	public List<EuGcpPrelever> findByIdTpagcp(Long idtpagcp);

    public List<EuGcpPrelever> findGcpPreleverByIdTpagcp(Long idtpagcp);
}
