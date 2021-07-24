package com.esmc.mcnp.services.obpsd;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuDetailAppro;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obpsd.EuDetailApproRepository;

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
