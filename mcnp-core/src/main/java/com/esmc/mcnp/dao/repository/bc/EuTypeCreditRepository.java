/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.bc;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.bc.EuTypeCredit;

/**
 *
 * @author Administrateur
 */
public interface EuTypeCreditRepository extends BaseRepository<EuTypeCredit, String> {

    public List<EuTypeCredit> findByTypeProduit(String type);

    @Query("select tc from EuTypeCredit tc where tc.codeTypeCredit not in ('CNCSnr', 'CNPG', 'REAPPRO')")
    public List<EuTypeCredit> fetchWithoutSalaire();

    @Query("select t.quotar from EuTypeCredit t where t.codeTypeCredit= :code")
    public Double findQuotaRByTypeCredit(@Param("code") String codeTypeCredit);

    @Query("select t.quotanr from EuTypeCredit t where t.codeTypeCredit= :code")
    public Double findQuotaNRByTypeCredit(@Param("code") String codeTypeCredit);
}
