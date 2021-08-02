/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.dao.repository.common;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.others.EuFrais;

/**
 *
 * @author USER
 */
public interface EuFraisRepository extends BaseRepository<EuFrais, Integer> {
	@Query("select f from EuFrais f where f.idProposition= :idProp")
	public EuFrais findByIdProposition(@Param("idProp") Long idProposition);
}
