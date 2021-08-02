package com.esmc.mcnp.infrastructure.services.oi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.oi.EuTypeBnpRepository;
import com.esmc.mcnp.domain.entity.oi.EuTypeBnp;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euTypeBnpService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuTypeBnpServiceImpl extends BaseServiceImpl<EuTypeBnp, String> implements EuTypeBnpService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuTypeBnpRepository typeBnpRepo;

	@Override
	protected BaseRepository<EuTypeBnp, String> getRepository() {
		return typeBnpRepo;
	}

}
