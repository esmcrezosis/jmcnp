package com.esmc.mcnp.infrastructure.services.smcipn;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.smcipn.EuDemandeSmcipnRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuDemandeSmcipn;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

@Service
public class EuDemandeSmcipnServiceImplImpl extends CrudServiceImpl<EuDemandeSmcipn, Long>
		implements EuDemandeSmcipnService {

	protected EuDemandeSmcipnRepository demandeSmcipnRepository;

	public EuDemandeSmcipnServiceImplImpl(EuDemandeSmcipnRepository demandeSmcipnRepository) {
		super(demandeSmcipnRepository);
		this.demandeSmcipnRepository = demandeSmcipnRepository;
	}

}
