/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.dao.repository.obps;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obps.EuArticlesVendu;

/**
 *
 * @author Administrateur
 */
public interface EuArticlesVenduRepository extends BaseRepository<EuArticlesVendu, Long>  {
    
     @Query("select max(a.idArticleVendu) from EuArticlesVendu a")
     public Long getLastEuArticleVenduInsertedId();
     
     public List<EuArticlesVendu> findByEuBon_BonNumero(String numero);
}
