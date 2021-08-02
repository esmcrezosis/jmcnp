package com.esmc.mcnp.infrastructure.services.obpsd;

import java.util.List;

import com.esmc.mcnp.domain.entity.obpsd.EuReglementWari;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuReglementWariService extends BaseService<EuReglementWari, Long> {
	List<EuReglementWari> findByNumeroTransaction(String numero);

	List<EuReglementWari> findByNumeroTraite(String numero);
}