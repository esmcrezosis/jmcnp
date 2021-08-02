package com.esmc.mcnp.infrastructure.services.obpsd;

import java.util.List;

import com.esmc.mcnp.domain.entity.obpsd.EuDetailGcpPbf;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuDetailGcpPbfService extends BaseService<EuDetailGcpPbf, Long> {
	public Long getLastInsertedId();

	public List<EuDetailGcpPbf> findByEuGcpPbf_CodeGcpPbf(String codeGcpPbf);

	public List<EuDetailGcpPbf> findDetailByGcpPbfB(String codeGcpPbf);

	List<EuDetailGcpPbf> findDetailByEscompte(long id_escompte);

	List<EuDetailGcpPbf> findDetailByEchange(long id_echange);
}
