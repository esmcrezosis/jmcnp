package com.esmc.mcnp.infrastructure.services.acteurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuFormsDetailBudgetNatureRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuFormsDetailBudgetNature;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
