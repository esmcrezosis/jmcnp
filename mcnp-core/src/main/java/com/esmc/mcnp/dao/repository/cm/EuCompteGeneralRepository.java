/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.dao.repository.cm;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cm.EuCompteGeneral;
import com.esmc.mcnp.domain.entity.cm.EuCompteGeneralPK;

/**
 *
 * @author USER
 */
public interface EuCompteGeneralRepository extends BaseRepository<EuCompteGeneral, EuCompteGeneralPK> {
	@Query("select cg from EuCompteGeneral cg where cg.id.codeCompte like :codeCompte and cg.id.codeTypeCompte like :codeTypeCompte and cg.id.service like :service")
	public EuCompteGeneral findById(@Param("codeCompte") String codeCompte,
			@Param("codeTypeCompte") String codeTypeCompte, @Param("service") String service);
}
