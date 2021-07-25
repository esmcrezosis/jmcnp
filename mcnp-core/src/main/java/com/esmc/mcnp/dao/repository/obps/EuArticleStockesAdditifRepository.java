/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.repositories.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obps.EuArticleStockesAdditif;
import com.esmc.mcnp.repositories.base.BaseRepository;


/**
 *
 * @author Administrateur
 */
public interface EuArticleStockesAdditifRepository extends BaseRepository<EuArticleStockesAdditif, Long>  {
      
 
    @Query("select a from EuArticleStockesAdditif a where reference =:reference")
    public List<EuArticleStockesAdditif> findArticleStockesAdditifByReference(@Param("reference") String reference);
   
    @Query("select a from EuArticleStockesAdditif a where codeMembreMorale =:codeMembre and reference =:reference")
    public EuArticleStockesAdditif findArticleStockesAdditifByMembreAndReference(@Param("codeMembre") String codeMembre, @Param("reference") String reference);


}
