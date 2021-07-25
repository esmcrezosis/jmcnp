/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.others;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.op.EuDetailAppelNn;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuDetailAppelNnRepository extends BaseRepository<EuDetailAppelNn, Long> {

    @Query("select max(d.idDetailAppelNn) from EuDetailAppelNn d")
    public Long findLastDetailAppelNnInsertId();

    @Query("select sum(d.montantApport) from EuDetailAppelNn d join d.euAppelNn a where a.idAppelNn = :idAppel")
    public Double sumMontNnCollectByAppelNn(@Param("idAppel") Long idAppelNn);
}
