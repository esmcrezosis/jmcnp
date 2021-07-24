/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.smcipn;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.others.EuProposition;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author Administrateur
 */
public interface EuPropositionRepository extends BaseRepository<EuProposition, Integer> {

    @Query("select p from EuProposition p join p.euAppelOffre o where o.idAppelOffre = :idAppelOf and p.choixProposition=1")
    public EuProposition findPropositionByNumeroAppelOffre(@Param("idAppelOf") Long numeroAppel);
}
