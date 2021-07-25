package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.pc.EuReleveescompte;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.pc.EuReleveescompteRepository;

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
