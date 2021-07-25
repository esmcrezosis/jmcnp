package com.esmc.mcnp.services.obps;

import java.util.List;
import com.esmc.mcnp.model.obps.EuCommandeNrOpi;
import com.esmc.mcnp.model.obpsd.EuTraite;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCommandeNrOpiService extends BaseService<EuCommandeNrOpi, Integer> {
	
	public List<EuTraite> findTraiteByCodeCommande(String codeCommande);
}
