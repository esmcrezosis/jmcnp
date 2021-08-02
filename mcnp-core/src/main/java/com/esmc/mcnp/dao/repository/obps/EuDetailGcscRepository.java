package com.esmc.mcnp.dao.repository.obps;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obps.EuDetailGcsc;

public interface EuDetailGcscRepository  extends BaseRepository<EuDetailGcsc, Long> {
	@Query("select max(dg.idDetailGcsc) from EuDetailGcsc dg")
	public Long getLastInsertedId();
}
