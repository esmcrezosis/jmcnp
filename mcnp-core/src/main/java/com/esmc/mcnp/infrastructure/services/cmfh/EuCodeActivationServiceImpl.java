package com.esmc.mcnp.infrastructure.services.cmfh;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.cmfh.EuCodeActivationRepository;
import com.esmc.mcnp.domain.entity.acteur.EuCodeActivation;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
