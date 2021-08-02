package com.esmc.mcnp.infrastructure.services.smcipn;

import com.esmc.mcnp.domain.entity.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuSmcipnpwiService extends BaseService<EuSmcipnpwi, String> {
	public String findLastByCodeMembreAndTypeSmcipn(String codeMembre, String type);

	public EuSmcipnpwi findByNumeroAppel(String numeroAppel);
}
