package com.esmc.mcnp.infrastructure.services.bc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.bc.EuCnpEntreeRepository;
import com.esmc.mcnp.domain.entity.bc.EuCnpEntree;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euCnpEntreeService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCnpEntreeServiceImpl extends BaseServiceImpl<EuCnpEntree, Long> implements EuCnpEntreeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuCnpEntreeRepository cnpEntRepo;
	
	@Override
	public Long getLastInsertedId() {
		return cnpEntRepo.getLastInsertedId();
	}

	@Override
	protected BaseRepository<EuCnpEntree, Long> getRepository() {
		return cnpEntRepo;
	}

}
