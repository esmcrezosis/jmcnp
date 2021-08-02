/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.esmc.mcnp.dao.repository.others;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuCompensation;

/**
 *
 * @author mawuli
 */
public interface EuCompensationRepository extends BaseRepository<EuCompensation, Long> {
	@Query("select max(c.idCompens) from EuCompensation c")
	public Long getLastCompensationInserted();

	@Query("select c from EuCompensation c where c.soldeCompensation > 0 and c.resteNtf > 0")
	public List<EuCompensation> findAllCompensation();
}
