package com.esmc.mcnp.repositories.org;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.org.EuRegion;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuRegionRepository extends BaseRepository<EuRegion, Integer> {
	
	@Query("select r from EuRegion r left join fetch r.euPay p where r.idRegion = :id")
	EuRegion findByKey(@Param("id") Integer id);
}
