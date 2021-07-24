/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.common;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.bc.EuPrk;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuPrkRepository extends BaseRepository<EuPrk, Integer> {

	public List<EuPrk> findByEuTegc_CodeTegc(String codeTegc);

	@Query("select p From EuPrk p join fetch p.euTegc t where t.codeTegc like :codeTegc")
	public EuPrk findByCodeTegc(@Param("codeTegc") String codeTegc);
}
