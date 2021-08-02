/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.dao.repository.common;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.config.EuParametres;
import com.esmc.mcnp.domain.entity.config.EuParametresPK;

/**
 *
 * @author USER
 */
public interface EuParametreRepository extends BaseRepository<EuParametres, EuParametresPK>{
    
    @Query("select p.montant from EuParametres p where p.id.codeParam = 'tauxoperation'")
    Double getMontantTauxOperation();
    
    @Query("select p.montant From EuParametres p where p.id.codeParam like :code and p.id.libParam like :lib")
	Double findByCodeAndLib(@Param("code") String code, @Param("lib") String lib);

    @Query("select p.valeur from EuParametres p where p.id.codeParam = :code and p.id.libParam = :lib")
    String getValeurByCodeAndLib(@Param("code") String code, @Param("lib") String lib);
}
