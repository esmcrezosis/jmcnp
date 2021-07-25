/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.common;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.security.EuConfirmation;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuConfirmationRepository extends BaseRepository<EuConfirmation, Long> {
	@Query("select c from EuConfirmation c where c.idConfirmation = (select max(c.idConfirmation) from EuConfirmation c where c.codeMembre like :code and c.status =1)")
	public EuConfirmation getEuConfirmationEnCours(@Param("code") String code);
}
