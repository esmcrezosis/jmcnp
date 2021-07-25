/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.ba;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.ba.EuCapaDeclaration;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author Administrateur
 */
public interface EuCapaDeclarationRepository extends BaseRepository<EuCapaDeclaration, BigDecimal> {

    @Query("select max(cd.idDeclaration) from EuCapaDeclaration cd")
    public Long getLastIdDeclarationInsertedId();

    @Query("select cd from EuCapaDeclaration cd where cd.referenceMat = :ref")
    public EuCapaDeclaration getEuCapaDeclarationByReference(@Param("ref") String reference);

    @Query("select cd from EuCapaDeclaration cd where cd.codebarMat = :codebarre")
    public EuCapaDeclaration getEuCapaDeclarationByCodebarre(@Param("codebarre") String codebarre);

}
