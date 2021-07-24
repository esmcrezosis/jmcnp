/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.repositories.others;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.others.EuOffreDemande;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuOffreDemandeRepository extends BaseRepository<EuOffreDemande, Long>{
    @Query("select max(od.idOffreDemande) from EuOffreDemande od")
    public Long getLastOffreInserted();
    public EuOffreDemande findByNumOffreDemande(String numeroOffreDemande);
}
