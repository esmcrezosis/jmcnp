/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.smcipn;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuFn;

/**
 *
 * @author USER
 */
public interface EuFnRepository extends BaseRepository<EuFn, Long> {

    @Query("select max(f.idFn) from EuFn f")
    public Long getLastFnInsertedId();

    @Query("select f from EuFn f where f.mtSolde > 0 and f.origineFn = 0")
    public List<EuFn> findByOrigineFn();
    
    @Query("select f from EuFn f where f.mtSolde > 0 and f.origineFn = :origine")
    public List<EuFn> findByOrigineFn(@Param("origine") Integer origine);

    @Query("select sum(f.mtSolde) from EuFn f where f.mtSolde > 0 and f.origineFn = 0")
    public Double findSumByOrigineFn();
    
    @Query("select sum(f.mtSolde) from EuFn f where f.mtSolde > 0 and f.origineFn = :origine")
    public Double findSumByOrigineFn(@Param("origine") Integer origine);

    @Query("select f from EuFn f where f.solde > 0 and f.codeSmcipn = :smcipn")
    public List<EuFn> findFnByCodeSmcipn(@Param("smcipn") String codeSmcipn);
}
