package com.esmc.mcnp.services.smcipn;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.model.smcipn.EuTypeBudget;
import com.esmc.mcnp.services.base.CrudService;

public interface EuTypeBudgetService extends CrudService<EuTypeBudget, Long> {
	List<EuTypeBudget> findByUser(Long id);

	Page<EuTypeBudget> findByUser(Long id, Pageable page);
}
