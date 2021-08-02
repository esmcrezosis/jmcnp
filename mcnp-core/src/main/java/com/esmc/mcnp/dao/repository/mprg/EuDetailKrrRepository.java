package com.esmc.mcnp.dao.repository.mprg;

import java.util.List;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.mprg.EuDetailKrr;

public interface EuDetailKrrRepository extends BaseRepository<EuDetailKrr, Long> {

	public List<EuDetailKrr> findByIdKrr(Long idKrr);
}
