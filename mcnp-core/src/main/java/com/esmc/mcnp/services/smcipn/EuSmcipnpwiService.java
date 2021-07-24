package com.esmc.mcnp.services.smcipn;

import com.esmc.mcnp.model.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.services.base.BaseService;

public interface EuSmcipnpwiService extends BaseService<EuSmcipnpwi, String> {
	public String findLastByCodeMembreAndTypeSmcipn(String codeMembre, String type);

	public EuSmcipnpwi findByNumeroAppel(String numeroAppel);
}
