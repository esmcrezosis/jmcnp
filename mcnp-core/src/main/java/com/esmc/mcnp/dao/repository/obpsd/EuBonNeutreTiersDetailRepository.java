package com.esmc.mcnp.dao.repository.obpsd;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreTiersDetail;

public interface EuBonNeutreTiersDetailRepository extends BaseRepository<EuBonNeutreTiersDetail, Integer> {

	@Query("select bnt from EuBonNeutreTiersDetail bnt where bnt.id.bonNeutreTiersId = :id")
	public List<EuBonNeutreTiersDetail> findByBonNeutreDetailId(@Param("id") Integer id);
}
