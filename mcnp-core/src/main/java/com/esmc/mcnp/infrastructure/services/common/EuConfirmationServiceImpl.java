package com.esmc.mcnp.infrastructure.services.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.common.EuConfirmationRepository;
import com.esmc.mcnp.domain.entity.security.EuConfirmation;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;


@Service("euConfirmationService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuConfirmationServiceImpl extends BaseServiceImpl<EuConfirmation, Long> implements EuConfirmationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuConfirmationRepository confirmationRepo;
	
	@Override
	protected BaseRepository<EuConfirmation, Long> getRepository() {
		return confirmationRepo;
	}

	@Override
	public EuConfirmation getConfirmationEnCours(String codeMembre) {
		return confirmationRepo.getEuConfirmationEnCours(codeMembre);
	}

}
