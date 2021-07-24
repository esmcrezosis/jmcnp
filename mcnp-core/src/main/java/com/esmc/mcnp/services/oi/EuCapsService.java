package com.esmc.mcnp.services.oi;

import com.esmc.mcnp.model.oi.EuCaps;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCapsService extends BaseService<EuCaps, String> {
	public EuCaps findByEuMembre2_CodeMembre(String codeMembre);

	public EuCaps findByCodeMembreBenef(String codeMembre);
}
