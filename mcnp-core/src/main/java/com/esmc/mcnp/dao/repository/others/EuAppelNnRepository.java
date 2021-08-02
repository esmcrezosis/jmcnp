/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.others;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.op.EuAppelNn;
import com.esmc.mcnp.domain.entity.op.EuDetailAppelNn;

/**
 *
 * @author USER
 */
public interface EuAppelNnRepository extends BaseRepository<EuAppelNn, Integer> {

    @Query("select max(a.idAppelNn) from EuAppelNn a")
    public Long findLastAppelNnInsertId();
    
    @Query("select a from EuAppelNn a join fetch a.euProposition p join fetch p.euAppelOffre o where o.numeroOffre = :numeroOf and p.disponible = 1 and p.preselection = 1")
    public EuAppelNn findAppelNnByNumeroOffre(@Param("numeroOf") String numeroOffre);

    @Query("select p.euDetailAppelNns from EuAppelNn p where p.idAppelNn = :idappel")
    public List<EuDetailAppelNn> findDetailAppelByappel(@Param("idappel") Integer id_appel);
}
