package com.esmc.mcnp.services.odd;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.odd.EuCentrales;
import com.esmc.mcnp.repositories.odd.EuCentralesRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

@Service
public class EuCentralesServiceImplImpl extends CrudServiceImpl<EuCentrales, Integer> implements EuCentralesService {

	private EuCentralesRepository centralesRepository;
	protected EuCentralesServiceImplImpl(EuCentralesRepository centralesRepository) {
		super(centralesRepository);
		this.centralesRepository = centralesRepository;
	}

}
