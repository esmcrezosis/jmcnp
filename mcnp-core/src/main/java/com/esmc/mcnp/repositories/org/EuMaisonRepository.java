/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.repositories.org;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.acteur.EuMaison;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author Administrateur
 */
public interface EuMaisonRepository extends BaseRepository<EuMaison, Long> {
@Query("select m from EuMaison m where m.codeMembre = :codeMembre")
public EuMaison getMaisonByCodemembre(@Param("codeMembre") String codeMembre);    
}
