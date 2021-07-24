package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.smcipn.EuFormsBudgetNature;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.smcipn.EuFormsBudgetNatureRepository;

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
