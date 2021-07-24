package com.esmc.mcnp.repositories.obpsd;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.obpsd.EuEchange;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuEchangeRepository extends BaseRepository<EuEchange, Long> {
    @Query("select max(e.idEchange) from EuEchange e")
    public Long findLastEchangeInsertedId();
}
