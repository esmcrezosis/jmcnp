package com.esmc.mcnp.services.obps;

import com.esmc.mcnp.model.others.EuAchatInterim;
import com.esmc.mcnp.services.base.BaseService;

public interface EuAchatInterimService extends BaseService<EuAchatInterim, Long> {

	public EuAchatInterim findAchatInterimByCodeAchat(String codeAchat);
}
