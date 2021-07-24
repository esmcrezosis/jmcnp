package com.esmc.mcnp.services.obpsd;

import java.util.List;

import com.esmc.mcnp.model.obpsd.EuReglementWari;
import com.esmc.mcnp.services.base.BaseService;

public interface EuReglementWariService extends BaseService<EuReglementWari, Long> {
	List<EuReglementWari> findByNumeroTransaction(String numero);

	List<EuReglementWari> findByNumeroTraite(String numero);
}