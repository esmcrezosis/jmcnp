package com.esmc.mcnp.dao.repository.obpsd;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.ba.EuDetailVentesms;

@Repository
public interface EuDetailVentesmsRepository extends BaseRepository<EuDetailVentesms, Long> {
	@Query("select max(s.idDetailVtsms) from EuDetailVentesms s")
	public Long getLastDetVenteSmsmoneyInsertedId();
}
