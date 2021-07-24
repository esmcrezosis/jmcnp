package com.esmc.mcnp.services.oi;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.oi.EuBnpSqmax;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.common.EuSqmaxRepository;

@Service("euBnpSqmaxService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuBnpSqmaxServiceImpl extends BaseServiceImpl<EuBnpSqmax, Long> implements EuBnpSqmaxService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuSqmaxRepository sqmaxRepo;
	@Override
	public Long findLastSQMaxIdInserted() {
		return sqmaxRepo.findLastSQMaxIdInserted();
	}

	@Override
	protected BaseRepository<EuBnpSqmax, Long> getRepository() {
		return sqmaxRepo;
	}

}
