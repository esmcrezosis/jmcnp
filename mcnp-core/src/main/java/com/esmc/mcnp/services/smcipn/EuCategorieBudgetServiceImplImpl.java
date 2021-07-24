package com.esmc.mcnp.services.smcipn;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.smcipn.EuCategorieBudget;
import com.esmc.mcnp.repositories.smcipn.EuCategorieBudgetRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

@Service
public class EuCategorieBudgetServiceImplImpl extends CrudServiceImpl<EuCategorieBudget, Integer>
		implements EuCategorieBudgetService {

	protected EuCategorieBudgetRepository categorieBudgetRepo;

	public EuCategorieBudgetServiceImplImpl(EuCategorieBudgetRepository categorieBudgetRepo) {
		super(categorieBudgetRepo);
		this.categorieBudgetRepo = categorieBudgetRepo;
	}

}
