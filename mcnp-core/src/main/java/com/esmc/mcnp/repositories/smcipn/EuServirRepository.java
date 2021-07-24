/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.smcipn;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.model.smcipn.EuFn;
import com.esmc.mcnp.model.smcipn.EuServir;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 *
 * @author USER
 */
public interface EuServirRepository extends BaseRepository<EuServir, Long> {

	public List<EuFn> findByEuSmcipnpwi_CodeSmcipn(String codeSmcipn);

	@Query("select max(e.id) from EuServir e")
	public Long getLastEuServirInsertedId();
}
