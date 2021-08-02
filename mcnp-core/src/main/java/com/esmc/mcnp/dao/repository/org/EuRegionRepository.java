package com.esmc.mcnp.dao.repository.org;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.org.EuRegion;

public interface EuRegionRepository extends BaseRepository<EuRegion, Integer> {
	
	@Query("select r from EuRegion r left join fetch r.euPay p where r.idRegion = :id")
	EuRegion findByKey(@Param("id") Integer id);
}
