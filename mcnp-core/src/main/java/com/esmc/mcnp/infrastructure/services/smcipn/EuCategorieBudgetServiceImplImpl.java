package com.esmc.mcnp.infrastructure.services.smcipn;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.smcipn.EuCategorieBudgetRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuCategorieBudget;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

@Service
public class EuCategorieBudgetServiceImplImpl extends CrudServiceImpl<EuCategorieBudget, Integer>
		implements EuCategorieBudgetService {

	protected EuCategorieBudgetRepository categorieBudgetRepo;

	public EuCategorieBudgetServiceImplImpl(EuCategorieBudgetRepository categorieBudgetRepo) {
		super(categorieBudgetRepo);
		this.categorieBudgetRepo = categorieBudgetRepo;
	}

}
