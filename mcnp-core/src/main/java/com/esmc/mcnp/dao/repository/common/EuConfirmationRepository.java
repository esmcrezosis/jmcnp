/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.common;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.security.EuConfirmation;

/**
 *
 * @author USER
 */
public interface EuConfirmationRepository extends BaseRepository<EuConfirmation, Long> {
	@Query("select c from EuConfirmation c where c.idConfirmation = (select max(c.idConfirmation) from EuConfirmation c where c.codeMembre like :code and c.status =1)")
	public EuConfirmation getEuConfirmationEnCours(@Param("code") String code);
}
