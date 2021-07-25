package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.others.EuObjet;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obps.EuObjetRepository;

@Service("euObjetService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class EuObjetServiceImpl extends BaseServiceImpl<EuObjet, Integer> implements EuObjetService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuObjetRepository objetRepo;

	@Override
	protected BaseRepository<EuObjet, Integer> getRepository() {
		return objetRepo;
	}

}
