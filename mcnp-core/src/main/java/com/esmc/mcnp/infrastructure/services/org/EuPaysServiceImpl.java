package com.esmc.mcnp.infrastructure.services.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.org.EuPaysRepository;
import com.esmc.mcnp.domain.entity.org.EuPays;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
