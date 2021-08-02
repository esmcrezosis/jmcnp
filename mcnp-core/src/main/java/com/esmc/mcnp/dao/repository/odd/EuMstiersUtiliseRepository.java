package com.esmc.mcnp.dao.repository.odd;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.odd.EuMstiersUtilise;

public interface EuMstiersUtiliseRepository extends BaseRepository<EuMstiersUtilise, Integer> {

    @Query("select max(m.idMstiersUtilise) From EuMstiersUtilise m")
    Integer getLastInsertId();
}
