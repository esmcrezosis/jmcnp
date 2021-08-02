/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.dao.repository.obpsd;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuGcpPbfCompense;

/**
 *
 * @author USER
 */
public interface EuGcpPbfCompenseRepository extends BaseRepository<EuGcpPbfCompense, Long>{
    @Query("select max(gc.idGcpCompense) from EuGcpPbfCompense gc")
    public Long getLastInsertedId();
    
}
