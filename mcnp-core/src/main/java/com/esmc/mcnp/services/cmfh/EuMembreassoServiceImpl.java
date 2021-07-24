package com.esmc.mcnp.services.cmfh;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.acteur.EuMembreasso;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.cmfh.EuMembreassoRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuMembreassoServiceImpl extends BaseServiceImpl<EuMembreasso, Long> implements EuMembreassoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuMembreassoRepository membreAssoRepo;

	@Override
	public EuMembreasso findByLogin(String login) {
		return membreAssoRepo.findByMembreassoLogin(login);
	}

	@Override
	protected BaseRepository<EuMembreasso, Long> getRepository() {
		return membreAssoRepo;
	}

}
