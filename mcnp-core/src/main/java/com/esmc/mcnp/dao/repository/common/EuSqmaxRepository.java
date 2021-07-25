/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.repositories.common;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.oi.EuBnpSqmax;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuSqmaxRepository extends BaseRepository<EuBnpSqmax, Long>{
    @Query("select max(s.idSqmax) from EuBnpSqmax s where s.idSqmax is not null")
    public Long findLastSQMaxIdInserted();
}
