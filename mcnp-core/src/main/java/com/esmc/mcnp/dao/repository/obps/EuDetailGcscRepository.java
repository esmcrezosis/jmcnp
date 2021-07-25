package com.esmc.mcnp.repositories.obps;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.obps.EuDetailGcsc;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuDetailGcscRepository  extends BaseRepository<EuDetailGcsc, Long> {
	@Query("select max(dg.idDetailGcsc) from EuDetailGcsc dg")
	public Long getLastInsertedId();
}
