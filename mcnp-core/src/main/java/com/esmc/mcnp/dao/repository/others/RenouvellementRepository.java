package com.esmc.mcnp.dao.repository.others;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.bc.EuRenouvellement;

public interface RenouvellementRepository extends BaseRepository<EuRenouvellement, Long> {

    @Query("select max(r.idRenouvellement) from EuRenouvellement r")
    public Long getLastEuRenouvllemmentInsertedId();
}
