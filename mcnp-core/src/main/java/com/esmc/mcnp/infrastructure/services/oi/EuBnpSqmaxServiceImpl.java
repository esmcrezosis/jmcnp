package com.esmc.mcnp.infrastructure.services.oi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.common.EuSqmaxRepository;
import com.esmc.mcnp.domain.entity.oi.EuBnpSqmax;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
