/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.others;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.bc.EuDomiciliation;

/**
 *
 * @author USER
 */
public interface EuDomiciliationRepository extends BaseRepository<EuDomiciliation, String> {

    @Query("select d from EuDomiciliation d join fetch d.euSmcipnpwi s join d.euProposition p where d.euMembreMorale.codeMembreMorale= :codeMembre and p.idProposition= :idProp")
    public EuDomiciliation findDomiByMembreAndProposition(@Param("codeMembre") String codeMembre, @Param("idProp") Integer idProposition);
    
    @Query("select d from EuDomiciliation d join d.euSmcipnpwi s where d.euMembreMorale.codeMembreMorale= :codeMembre and s.codeSmcipn= :codeSmcipn")
    public EuDomiciliation findDomiByCodeSmcipn(@Param("codeMembre") String codeMembre, @Param("codeSmcipn") String codeSmcipn);

    @Query("select d from EuDomiciliation d where d.codeDomicilier= :codeDom")
    public EuDomiciliation findByCodeDomiciliation(@Param("codeDom") String codeDomicilier);
    
   
}
