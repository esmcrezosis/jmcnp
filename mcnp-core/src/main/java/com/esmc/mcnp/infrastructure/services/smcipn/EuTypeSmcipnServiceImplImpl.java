package com.esmc.mcnp.services.smcipn;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.smcipn.EuTypeSmcipn;
import com.esmc.mcnp.repositories.smcipn.EuTypeSmcipnRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

@Service
public class EuTypeSmcipnServiceImplImpl extends CrudServiceImpl<EuTypeSmcipn, Integer> implements EuTypeSmcipnService {

	protected EuTypeSmcipnRepository typeSmcipnRepository;

	public EuTypeSmcipnServiceImplImpl(EuTypeSmcipnRepository typeSmcipnRepository) {
		super(typeSmcipnRepository);
		this.typeSmcipnRepository = typeSmcipnRepository;
	}

}
