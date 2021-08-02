/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.others;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.others.EuFiliereTypeCredit;

/**
 *
 * @author Administrateur
 */
public interface EuFiliereTypeCreditRepository extends BaseRepository<EuFiliereTypeCredit, Integer> {

    @Query("select ft from EuFiliereTypeCredit ft join ft.euFiliere f where f.idFiliere= :filiere")
    public EuFiliereTypeCredit findFiliereTypeCreditByFiliere(@Param("filiere") Long idFiliere);

    
}
