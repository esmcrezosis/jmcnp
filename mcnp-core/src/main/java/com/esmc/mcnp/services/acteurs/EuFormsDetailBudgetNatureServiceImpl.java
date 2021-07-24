package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.smcipn.EuFormsDetailBudgetNature;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.smcipn.EuFormsDetailBudgetNatureRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuFormsDetailBudgetNatureServiceImpl extends BaseServiceImpl<EuFormsDetailBudgetNature, Long>
		implements EuFormsDetailBudgetNatureService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuFormsDetailBudgetNatureRepository formBudgetNatRepo;

	@Override
	protected BaseRepository<EuFormsDetailBudgetNature, Long> getRepository() {
		return formBudgetNatRepo;
	}

}
