package com.esmc.mcnp.infrastructure.services.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.pc.EuReleveescompteRepository;
import com.esmc.mcnp.domain.entity.pc.EuReleveescompte;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euReleveescompteService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuReleveescompteServiceImpl extends BaseServiceImpl<EuReleveescompte, Integer>
		implements EuReleveescompteservice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuReleveescompteRepository relescRepo;

	@Override
	protected BaseRepository<EuReleveescompte, Integer> getRepository() {
		return relescRepo;
	}

}
