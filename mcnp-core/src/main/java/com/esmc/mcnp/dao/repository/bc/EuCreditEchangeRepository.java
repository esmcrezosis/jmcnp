/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.dao.repository.bc;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.bc.EuCreditEchange;

/**
 *
 * @author USER
 */
public interface EuCreditEchangeRepository extends BaseRepository<EuCreditEchange, Long>{
    @Query("select max(ce.idCreditEchange) from EuCreditEchange ce")
    public Long getLastInsertedId();
}
