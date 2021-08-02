package com.esmc.mcnp.dao.repository.smcipn;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuFormsDetailBudgetNature;

public interface EuFormsDetailBudgetNatureRepository extends BaseRepository<EuFormsDetailBudgetNature, Long> {

	@Query("select sum(totalBudgetNature) From EuFormsDetailBudgetNature b where b.euFormsBudgetNature.idFormsBudgetNature = :id")
	public Double getSumDetailBudgetNature(@Param("id") Long idBudgetNature);
}
