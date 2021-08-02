/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.oi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.oi.EuDetailBnp;

/**
 *
 * @author Mawuli
 */
public interface EuDetailBnpRepository extends BaseRepository<EuDetailBnp, Long> {

	@Query("select max(d.periode) from EuDetailBnp d where d.euCompteCredit.idCredit = :id and d.euBnp.codeBnp like :code")
	public Integer getLastPeriode(@Param("id") long idCredit, @Param("code") String codeBnp);
}
