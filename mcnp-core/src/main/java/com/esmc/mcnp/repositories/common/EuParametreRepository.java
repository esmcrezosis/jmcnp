/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.repositories.common;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.config.EuParametres;
import com.esmc.mcnp.model.config.EuParametresPK;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuParametreRepository extends BaseRepository<EuParametres, EuParametresPK>{
    
    @Query("select montant from EuParametres p where p.id.codeParam = 'tauxoperation'")
    public Double getMontantTauxOperation();
    
    @Query("select p.montant From EuParametres p where p.id.codeParam like :code and p.id.libParam like :lib")
	public Double findByCodeAndLib(@Param("code") String code, @Param("lib") String lib);
}
