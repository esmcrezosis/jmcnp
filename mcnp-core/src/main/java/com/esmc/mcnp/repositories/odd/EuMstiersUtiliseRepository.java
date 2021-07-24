package com.esmc.mcnp.repositories.odd;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.odd.EuMstiersUtilise;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuMstiersUtiliseRepository extends BaseRepository<EuMstiersUtilise, Integer> {

    @Query("select max(m.idMstiersUtilise) From EuMstiersUtilise m")
    Integer getLastInsertId();
}
