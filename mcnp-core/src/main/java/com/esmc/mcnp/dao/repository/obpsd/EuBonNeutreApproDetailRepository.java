/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.dao.repository.obpsd;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreApproDetail;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreApproDetailPK;

/**
 *
 * @author mawuli
 */
public interface EuBonNeutreApproDetailRepository extends BaseRepository<EuBonNeutreApproDetail, EuBonNeutreApproDetailPK> {

	@Query("select bna from EuBonNeutreApproDetail bna where bna.id.bonNeutreApproId = :id")
	List<EuBonNeutreApproDetail> findByBonNeutreDetailId(@Param("id") Integer id);
}
