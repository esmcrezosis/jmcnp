package com.esmc.mcnp.services.obps;

import java.util.List;

import com.esmc.mcnp.model.obps.EuGcpPrelever;
import com.esmc.mcnp.services.base.BaseService;

public interface EuGcpPreleverService extends BaseService<EuGcpPrelever, Long> {
	public List<EuGcpPrelever> findByIdTpagcp(Long idtpagcp);

    public List<EuGcpPrelever> findGcpPreleverByIdTpagcp(Long idtpagcp);
}
