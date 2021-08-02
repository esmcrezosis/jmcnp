package com.esmc.mcnp.infrastructure.services.smcipn;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.domain.entity.smcipn.EuTypeBudget;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

public interface EuTypeBudgetService extends CrudService<EuTypeBudget, Long> {
	List<EuTypeBudget> findByUser(Long id);

	Page<EuTypeBudget> findByUser(Long id, Pageable page);
}
