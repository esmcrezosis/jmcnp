package com.esmc.mcnp.infrastructure.services.obpsd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obpsd.EuDetailApproRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuDetailAppro;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euDetailApproService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuDetailApproServiceImpl extends BaseServiceImpl<EuDetailAppro, Long> implements EuDetailApproService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private EuDetailApproRepository detailApproRepository;

	@Override
	protected BaseRepository<EuDetailAppro, Long> getRepository() {
		return detailApproRepository;
	}
}
