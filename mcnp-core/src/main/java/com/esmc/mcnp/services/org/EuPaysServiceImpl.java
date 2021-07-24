package com.esmc.mcnp.services.org;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.org.EuPays;
import com.esmc.mcnp.repositories.org.EuPaysRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service("euPaysService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuPaysServiceImpl extends BaseServiceImpl<EuPays, Integer> implements EuPaysService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuPaysRepository paysRepo;

	@Override
	protected BaseRepository<EuPays, Integer> getRepository() {
		return paysRepo;
	}

}
