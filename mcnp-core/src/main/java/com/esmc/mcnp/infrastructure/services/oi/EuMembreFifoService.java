package com.esmc.mcnp.infrastructure.services.oi;

import java.util.List;

import com.esmc.mcnp.domain.entity.odd.EuMembreFifo;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuMembreFifoService extends BaseService<EuMembreFifo, Long> {
	List<EuMembreFifo> findByNonAffecter(Integer valider, Integer substituer, Integer affecter);

	List<EuMembreFifo> findByNonAffecter(Integer valider, Integer substituer, List<Integer> affecter);
}
