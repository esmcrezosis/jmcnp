package com.esmc.mcnp.repositories.mprg;

import java.util.List;

import com.esmc.mcnp.model.mprg.EuDetailKrr;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuDetailKrrRepository extends BaseRepository<EuDetailKrr, Long> {

	public List<EuDetailKrr> findByIdKrr(Long idKrr);
}
