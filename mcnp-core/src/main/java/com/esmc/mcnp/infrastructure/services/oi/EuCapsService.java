package com.esmc.mcnp.infrastructure.services.oi;

import com.esmc.mcnp.domain.entity.oi.EuCaps;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCapsService extends BaseService<EuCaps, String> {
	public EuCaps findByEuMembre2_CodeMembre(String codeMembre);

	public EuCaps findByCodeMembreBenef(String codeMembre);
}
