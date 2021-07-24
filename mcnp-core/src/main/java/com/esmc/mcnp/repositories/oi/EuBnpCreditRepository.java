/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.repositories.oi;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.oi.EuBnpCredit;
import com.esmc.mcnp.model.oi.EuBnpCreditPK;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuBnpCreditRepository extends BaseRepository<EuBnpCredit, EuBnpCreditPK> {
	@Query("select b from EuBnpCredit b join fetch b.euCompteCredit where b.id.codeBnp like :code")
	public EuBnpCredit getBnpCreditByCodeBnp(@Param("code") String code);
}
