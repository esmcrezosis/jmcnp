package com.esmc.mcnp.services.smcipn;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.smcipn.EuTypeBudget;
import com.esmc.mcnp.repositories.smcipn.EuTypeBudgetRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

@Service
public class EuTypeBudgetServiceImplImpl extends CrudServiceImpl<EuTypeBudget, Long> implements EuTypeBudgetService {

	protected EuTypeBudgetRepository typeBudgetRepository;

	protected EuTypeBudgetServiceImplImpl(EuTypeBudgetRepository typeBudgetRepository) {
		super(typeBudgetRepository);
		this.typeBudgetRepository = typeBudgetRepository;
	}

	@Override
	public List<EuTypeBudget> findByUser(Long id) {
		return typeBudgetRepository.findByUser_IdUtilisateur(id);
	}

	@Override
	public Page<EuTypeBudget> findByUser(Long id, Pageable page) {
		return typeBudgetRepository.findByUser_IdUtilisateur(id, page);
	}

}
