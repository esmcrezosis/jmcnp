/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.repositories.smcipn;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuSmcipnwiRepository extends BaseRepository<EuSmcipnpwi, String> {
	@Query("select max(s.codeSmcipn) from EuSmcipnpwi s where s.codeMembre= :membre and s.typeSmcipn= :type")
	public String findLastByCodeMembreAndTypeSmcipn(@Param("membre") String codeMembre, @Param("type") String type);

	public EuSmcipnpwi findByNumeroAppel(String numeroAppel);
}
