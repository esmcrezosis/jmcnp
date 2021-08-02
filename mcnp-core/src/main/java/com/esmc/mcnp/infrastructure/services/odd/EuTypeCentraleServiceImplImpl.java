package com.esmc.mcnp.infrastructure.services.odd;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.odd.EuTypeCentraleRepository;
import com.esmc.mcnp.domain.entity.odd.EuTypeCentrale;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

@Service
public class EuTypeCentraleServiceImplImpl extends CrudServiceImpl<EuTypeCentrale, Integer>
		implements EuTypeCentraleService {

	private EuTypeCentraleRepository typeCentraleRepository;

	protected EuTypeCentraleServiceImplImpl(EuTypeCentraleRepository typeCentraleRepository) {
		super(typeCentraleRepository);
		this.typeCentraleRepository = typeCentraleRepository;
	}

}
