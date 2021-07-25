package com.esmc.mcnp.repositories.others;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.bc.EuRenouvellement;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface RenouvellementRepository extends BaseRepository<EuRenouvellement, Long> {

    @Query("select max(r.idRenouvellement) from EuRenouvellement r")
    public Long getLastEuRenouvllemmentInsertedId();
}
