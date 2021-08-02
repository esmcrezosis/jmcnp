package com.esmc.mcnp.dao.repository.obpsd;

import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuReglementWari;

public interface EuReglementWariRepository extends BaseRepository<EuReglementWari, Long> {
	List<EuReglementWari> findByNumeroTransaction(String numeroTransaction);
}
