/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.others;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.others.EuProcuration;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author Administrateur
 */
public interface EuProcurationRepository extends BaseRepository<EuProcuration, Integer> {

    @Query("select p from EuProcuration p where p.codeMembreMandant like :codeMembre and p.activer = 1")
    public EuProcuration findProcurationByCodeMembre(@Param("codeMembre") String codeMembre);
}
