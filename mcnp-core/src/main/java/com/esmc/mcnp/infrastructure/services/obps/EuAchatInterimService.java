package com.esmc.mcnp.infrastructure.services.obps;

import com.esmc.mcnp.domain.entity.others.EuAchatInterim;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuAchatInterimService extends BaseService<EuAchatInterim, Long> {

	public EuAchatInterim findAchatInterimByCodeAchat(String codeAchat);
}
