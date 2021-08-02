package com.esmc.mcnp.infrastructure.services.acteurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuFormsBudgetNatureRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuFormsBudgetNature;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euFormsBudgetNatureService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuFormsBudgetNatureServiceImpl extends BaseServiceImpl<EuFormsBudgetNature, Integer>
		implements EuFormsBudgetNatureService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuFormsBudgetNatureRepository budgetNatureRepo;

	public EuFormsBudgetNatureServiceImpl() {
	}

	@Override
	protected BaseRepository<EuFormsBudgetNature, Integer> getRepository() {
		return budgetNatureRepo;
	}

}
