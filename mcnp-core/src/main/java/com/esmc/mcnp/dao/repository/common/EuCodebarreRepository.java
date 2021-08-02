/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obps.EuCodebarre;

/**
 *
 * @author Administrateur
 */
public interface EuCodebarreRepository extends BaseRepository<EuCodebarre, String> {

    /*@Query("select max(e.codebarre) from EuCodebarre e where e.typeCodebar='g'")
    public String getLastCodebarreInserted();*/
    
    @Query("select e from EuCodebarre e where e.codebarre like :codebarre")
    public List<EuCodebarre> getCodebarresByGros(@Param("codebarre") String codebarGros);
    
    @Query("select e from EuCodebarre e where e.codemembreDem = :codeMembre")
    public List<EuCodebarre> getCodebarres(@Param("codeMembre") String codeMembreDem);
	
    @Query("select max(e.codebarre) from EuCodebarre e where e.typeCodebar='sg'")
    public String getLastCodebarresSemiGros();
    
    @Query("select max(e.codebarre) from EuCodebarre e where e.typeCodebar='dt'")
    public String getLastCodebarresDetaillant();
    
    @Query("select max(e.codebarre) from EuCodebarre e where e.typeCodebar='g'")
    public String getLastCodebarresGros();
}
