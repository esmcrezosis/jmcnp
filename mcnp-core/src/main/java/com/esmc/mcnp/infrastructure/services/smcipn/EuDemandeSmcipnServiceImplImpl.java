package com.esmc.mcnp.services.smcipn;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.smcipn.EuDemandeSmcipn;
import com.esmc.mcnp.repositories.smcipn.EuDemandeSmcipnRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

@Service
public class EuDemandeSmcipnServiceImplImpl extends CrudServiceImpl<EuDemandeSmcipn, Long>
		implements EuDemandeSmcipnService {

	protected EuDemandeSmcipnRepository demandeSmcipnRepository;

	public EuDemandeSmcipnServiceImplImpl(EuDemandeSmcipnRepository demandeSmcipnRepository) {
		super(demandeSmcipnRepository);
		this.demandeSmcipnRepository = demandeSmcipnRepository;
	}

}
