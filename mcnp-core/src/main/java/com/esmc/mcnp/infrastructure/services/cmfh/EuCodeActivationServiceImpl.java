package com.esmc.mcnp.services.cmfh;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.acteur.EuCodeActivation;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.cmfh.EuCodeActivationRepository;
import com.esmc.mcnp.services.base.BaseServiceImpl;

@Service("euCodeActivationService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCodeActivationServiceImpl extends BaseServiceImpl<EuCodeActivation, Long>
		implements EuCodeActivationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuCodeActivationRepository codeActRepo;

	@Override
	protected BaseRepository<EuCodeActivation, Long> getRepository() {
		return codeActRepo;
	}

	@Override
	public EuCodeActivation findByMembre(String codeMembre) {
		if (StringUtils.isNotBlank(codeMembre)) {
			return codeActRepo.findByCodeMembre(codeMembre);
		}
		return null;
	}

}
