package com.esmc.mcnp.infrastructure.services.common;

import java.util.List;

import com.esmc.mcnp.domain.entity.obps.EuCodebarre;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCodebarreService extends BaseService<EuCodebarre, String> {
	
	public List<EuCodebarre> getCodebarresByGros(String codebarGros);

	public List<EuCodebarre> getCodebarres(String codeMembreDem);

	public String getLastCodebarresSemiGros();

	public String getLastCodebarresDetaillant();

	public String getLastCodebarresGros();
}
