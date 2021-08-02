/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.smcipn;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuSmc;
import com.esmc.mcnp.domain.entity.smcipn.EuUtiliser;

/**
 *
 * @author USER
 */
public interface EuUtiliserRepository extends BaseRepository<EuUtiliser, Long> {

	@Query("select max(u.idUtiliser) from EuUtiliser u")
	public Long findLastUtiliserInsertedId();

	public List<EuSmc> findByEuSmcipnpwi_CodeSmcipn(String codeSmcipn);

	public List<EuUtiliser> findByIdCredit(Long idCredit);
}
