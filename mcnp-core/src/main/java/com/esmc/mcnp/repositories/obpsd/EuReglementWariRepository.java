package com.esmc.mcnp.repositories.obpsd;

import java.util.List;

import com.esmc.mcnp.model.obpsd.EuReglementWari;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuReglementWariRepository extends BaseRepository<EuReglementWari, Long> {
	List<EuReglementWari> findByNumeroTransaction(String numeroTransaction);
}
