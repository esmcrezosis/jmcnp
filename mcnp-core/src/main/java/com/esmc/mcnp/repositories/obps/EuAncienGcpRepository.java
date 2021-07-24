package com.esmc.mcnp.repositories.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obps.EuAncienGcp;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuAncienGcpRepository extends BaseRepository<EuAncienGcp, Long> {

    List<EuAncienGcp> findByCodeMembre(String codeMembre);

    @Query("select sum(g.reste) From EuAncienGcp g where g.codeMembre like :membre")
    Double getSumResteByCodeMembre(@Param("membre") String codeMembre);
    
    @Query("select sum(g.montGcp) From EuAncienGcp g where g.codeMembre like :membre")
    Double getSumGcpByCodeMembre(@Param("membre") String codeMembre);
}
