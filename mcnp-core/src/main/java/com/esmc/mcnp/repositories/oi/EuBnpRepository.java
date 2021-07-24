/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.repositories.oi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.oi.EuBnp;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuBnpRepository extends BaseRepository<EuBnp, String> {
	@Query("select b from EuBnp b join fetch b.euTypeBnp where b.codeBnp like :codeBnp")
	public EuBnp getEuBnpWithTypeBnp(@Param("code") String codeBnp);
}
