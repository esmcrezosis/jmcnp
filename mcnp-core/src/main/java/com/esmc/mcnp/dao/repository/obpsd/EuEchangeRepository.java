package com.esmc.mcnp.dao.repository.obpsd;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuEchange;

public interface EuEchangeRepository extends BaseRepository<EuEchange, Long> {
    @Query("select max(e.idEchange) from EuEchange e")
    public Long findLastEchangeInsertedId();
}
