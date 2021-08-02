/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.dao.repository.common;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.sms.EuSms;

/**
 *
 * @author USER
 */
public interface EuSmsRepository extends BaseRepository<EuSms, Long>{
    @Query("select max(s.neng) from EuSms s")
    public Long getLastSmsInserted();
}
