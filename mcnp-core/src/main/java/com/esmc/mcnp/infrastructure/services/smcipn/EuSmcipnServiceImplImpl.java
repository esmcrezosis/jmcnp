package com.esmc.mcnp.infrastructure.services.smcipn;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.smcipn.EuSmcipnRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuSmcipn;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

@Service
public class EuSmcipnServiceImplImpl extends CrudServiceImpl<EuSmcipn, Long> implements EuSmcipnService {

	protected EuSmcipnRepository smcipnRepository;

	public EuSmcipnServiceImplImpl(EuSmcipnRepository smcipnRepository) {
		super(smcipnRepository);
		this.smcipnRepository = smcipnRepository;
	}

}
