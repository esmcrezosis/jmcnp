package com.esmc.mcnp.services.oi;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.oi.EuTypeBnp;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.oi.EuTypeBnpRepository;

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
