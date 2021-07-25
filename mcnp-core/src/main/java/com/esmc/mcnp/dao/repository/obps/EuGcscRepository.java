/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.smcipn.EuGcsc;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author Administrateur
 */
public interface EuGcscRepository extends BaseRepository<EuGcsc, Long> {

    @Query("select max(g.idGcsc) from EuGcsc g")
    public Long findLastGcscInsertedId();

    @Query("select g from EuGcsc g join g.euSmcipnpwi s where s.codeSmcipn = :smcipn")
    public EuGcsc findByEuSmcipn_CodeSmcipn(@Param("smcipn") String codeSmcipn);

    @Query("select g from EuGcsc g join g.euSmcipnpwi s where s.codeSmcipn = :smcipn and g.codeMembre= :membre")
    public EuGcsc findBySmcipnAndBenef(@Param("smcipn") String codeSmcipn, @Param("membre") String codeMembre);

    @Query("select g from EuGcsc g where g.codeMembre = :membre and g.solde<>0")
    public EuGcsc findByEuMembreMorale_CodeMembreMorale(@Param("membre") String codemembre);

    @Query("select g from EuGcsc g where g.codeMembre = :membre and g.solde<>0")
    public List<EuGcsc> findByCodeMembre(@Param("membre") String codemembre);

    @Query("select g from EuGcsc g where g.codeTegc = :tegc and g.solde<>0")
    public List<EuGcsc> findByTegc(@Param("tegc") String codeTegc);

    @Query("select sum(g.solde) from EuGcsc g where g.codeTegc = :tegc and g.solde<>0")
    public Double getSumGcscByTegc(@Param("tegc") String codeTegc);

    @Query("select sum(g.solde) from EuGcsc g where g.codeMembre = :membre and g.solde<>0")
    public Double getSumGcscByCodeMembre(@Param("membre") String codeMembre);

    @Query("select g from EuGcsc g where g.euSmcipnpwi.numeroAppel like :numeroAppel")
    public EuGcsc findByNumeroAppelOffre(@Param("numeroAppel") String numeroAppelOffre);
}
