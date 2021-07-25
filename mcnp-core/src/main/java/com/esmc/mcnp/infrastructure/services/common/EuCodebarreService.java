package com.esmc.mcnp.services.common;

import java.util.List;

import com.esmc.mcnp.model.obps.EuCodebarre;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCodebarreService extends BaseService<EuCodebarre, String> {
	
	public List<EuCodebarre> getCodebarresByGros(String codebarGros);

	public List<EuCodebarre> getCodebarres(String codeMembreDem);

	public String getLastCodebarresSemiGros();

	public String getLastCodebarresDetaillant();

	public String getLastCodebarresGros();
}
