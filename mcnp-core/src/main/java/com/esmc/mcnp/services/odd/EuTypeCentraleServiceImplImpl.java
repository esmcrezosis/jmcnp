package com.esmc.mcnp.services.odd;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.odd.EuTypeCentrale;
import com.esmc.mcnp.repositories.odd.EuTypeCentraleRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

@Service
public class EuTypeCentraleServiceImplImpl extends CrudServiceImpl<EuTypeCentrale, Integer>
		implements EuTypeCentraleService {

	private EuTypeCentraleRepository typeCentraleRepository;

	protected EuTypeCentraleServiceImplImpl(EuTypeCentraleRepository typeCentraleRepository) {
		super(typeCentraleRepository);
		this.typeCentraleRepository = typeCentraleRepository;
	}

}
