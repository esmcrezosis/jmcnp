/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.others;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.others.EuAccessKids;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author Administrateur
 */
public interface EuAccessKidsRepository extends BaseRepository<EuAccessKids, Long> {

    
    @Query("select max(e.neng) from EuAccessKids e")
    public Long getLastAccessKidInserted();
    
    @Query("select e from EuAccessKids e where e.serialnumber = :serial")
    public EuAccessKids getLecteurBySerialNumber(@Param("serial") String serialnumber);
}
