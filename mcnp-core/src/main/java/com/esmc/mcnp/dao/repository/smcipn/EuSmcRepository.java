package com.esmc.mcnp.dao.repository.smcipn;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuSmc;

@Repository
public interface EuSmcRepository extends BaseRepository<EuSmc, Long> {

    @Query("select max(e.idSmc) from EuSmc e")
    public Long getLastEuSmcInsertedId();
    
    @Query("select s from EuSmc s where s.montantSolde > 0 and s.origineSmc = 0")
    public List<EuSmc> findByOrigineSmc();
    
    @Query("select sum(s.montantSolde) from EuSmc s where s.montantSolde > 0 and s.origineSmc = 0")
    public Double findSumByOrigineSmc();
    
    @Query("select f from EuSmc f where f.solde > 0 and f.codeSmcipn = :codeSmcipn")
    public List<EuSmc> findSmcByCodeSmcipn(@Param("codeSmcipn") String codeSmcipn);
}
