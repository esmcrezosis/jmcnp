package com.esmc.mcnp.infrastructure.services.smcipn;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.smcipn.EuTypeSmcipnRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuTypeSmcipn;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

@Service
public class EuTypeSmcipnServiceImplImpl extends CrudServiceImpl<EuTypeSmcipn, Integer> implements EuTypeSmcipnService {

	protected EuTypeSmcipnRepository typeSmcipnRepository;

	public EuTypeSmcipnServiceImplImpl(EuTypeSmcipnRepository typeSmcipnRepository) {
		super(typeSmcipnRepository);
		this.typeSmcipnRepository = typeSmcipnRepository;
	}

}
