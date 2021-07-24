package com.esmc.mcnp.repositories.bc;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.bc.EuCnnc;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuCnncRepository extends BaseRepository<EuCnnc, Long> {

    @Query("select max(c.idCnnc) from EuCnnc c")
    public Long getLastEuCnncInsertedId();
}
