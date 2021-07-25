package com.esmc.mcnp.services.common;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.security.EuConfirmation;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.common.EuConfirmationRepository;


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
