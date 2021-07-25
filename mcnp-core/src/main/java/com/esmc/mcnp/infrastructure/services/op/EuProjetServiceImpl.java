package com.esmc.mcnp.services.op;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.op.EuProjet;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.op.EuProjetRepository;

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
