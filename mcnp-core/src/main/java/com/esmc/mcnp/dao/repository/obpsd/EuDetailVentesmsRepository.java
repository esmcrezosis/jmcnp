package com.esmc.mcnp.repositories.obpsd;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.model.ba.EuDetailVentesms;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Repository
public interface EuDetailVentesmsRepository extends BaseRepository<EuDetailVentesms, Long> {
	@Query("select max(s.idDetailVtsms) from EuDetailVentesms s")
	public Long getLastDetVenteSmsmoneyInsertedId();
}
