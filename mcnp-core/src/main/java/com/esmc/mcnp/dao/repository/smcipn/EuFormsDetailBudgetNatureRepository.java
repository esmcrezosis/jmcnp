package com.esmc.mcnp.repositories.smcipn;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.smcipn.EuFormsDetailBudgetNature;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuFormsDetailBudgetNatureRepository extends BaseRepository<EuFormsDetailBudgetNature, Long> {

	@Query("select sum(totalBudgetNature) From EuFormsDetailBudgetNature b where b.euFormsBudgetNature.idFormsBudgetNature = :id")
	public Double getSumDetailBudgetNature(@Param("id") Long idBudgetNature);
}
