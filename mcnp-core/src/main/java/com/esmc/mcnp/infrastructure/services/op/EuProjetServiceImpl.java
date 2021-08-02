package com.esmc.mcnp.infrastructure.services.op;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.op.EuProjetRepository;
import com.esmc.mcnp.domain.entity.op.EuProjet;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuProjetServiceImpl extends BaseServiceImpl<EuProjet, Integer> implements EuProjetService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuProjetRepository projetRepo;

	@Override
	protected BaseRepository<EuProjet, Integer> getRepository() {
		return projetRepo;
	}

}
