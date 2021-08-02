/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.bc;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.bc.EuCnpEntree;

/**
 *
 * @author USER
 */
public interface EuCnpEntreeRepository extends BaseRepository<EuCnpEntree, Long> {

    @Query("select max(ce.idCnpEntree) from EuCnpEntree ce")
    public Long getLastInsertedId();
}
