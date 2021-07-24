package com.esmc.mcnp.services.smcipn;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.smcipn.EuSmcipn;
import com.esmc.mcnp.repositories.smcipn.EuSmcipnRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

@Service
public class EuSmcipnServiceImplImpl extends CrudServiceImpl<EuSmcipn, Long> implements EuSmcipnService {

	protected EuSmcipnRepository smcipnRepository;

	public EuSmcipnServiceImplImpl(EuSmcipnRepository smcipnRepository) {
		super(smcipnRepository);
		this.smcipnRepository = smcipnRepository;
	}

}
