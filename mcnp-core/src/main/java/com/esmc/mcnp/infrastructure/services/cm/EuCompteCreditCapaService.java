package com.esmc.mcnp.infrastructure.services.cm;

import java.util.List;

import com.esmc.mcnp.domain.entity.cm.EuCompteCreditCapa;
import com.esmc.mcnp.domain.entity.cm.EuCompteCreditCapaPK;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCompteCreditCapaService extends BaseService<EuCompteCreditCapa, EuCompteCreditCapaPK> {
	public List<EuCompteCreditCapa> findCreditCapaByIdCredit(Long idCredit);
}
