/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.repositories.obps;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.others.EuAchatInterim;
import com.esmc.mcnp.repositories.base.BaseRepository;


/**
 *
 * @author Administrateur
 */
public interface EuAchatInterimRepository extends BaseRepository<EuAchatInterim, Long>  {
      
	 @Query("select a from EuAchatInterim a where codeAchat like :codeAchat")
	    public EuAchatInterim findByCodeAchat(@Param("codeAchat") String codeAchat);   

}
