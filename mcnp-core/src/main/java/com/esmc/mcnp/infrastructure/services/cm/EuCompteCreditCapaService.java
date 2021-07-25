package com.esmc.mcnp.services.cm;

import java.util.List;

import com.esmc.mcnp.model.cm.EuCompteCreditCapa;
import com.esmc.mcnp.model.cm.EuCompteCreditCapaPK;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCompteCreditCapaService extends BaseService<EuCompteCreditCapa, EuCompteCreditCapaPK> {
	public List<EuCompteCreditCapa> findCreditCapaByIdCredit(Long idCredit);
}
