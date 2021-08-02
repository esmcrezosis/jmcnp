package com.esmc.mcnp.dao.repository.bc;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.bc.EuCnnc;

public interface EuCnncRepository extends BaseRepository<EuCnnc, Long> {

    @Query("select max(c.idCnnc) from EuCnnc c")
    public Long getLastEuCnncInsertedId();
}
