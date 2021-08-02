package com.esmc.mcnp.infrastructure.services.cmfh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.cmfh.EuMembreassoRepository;
import com.esmc.mcnp.domain.entity.acteur.EuMembreasso;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
