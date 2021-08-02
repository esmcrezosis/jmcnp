package com.esmc.mcnp.infrastructure.services.odd;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.odd.EuCentralesRepository;
import com.esmc.mcnp.domain.entity.odd.EuCentrales;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

@Service
public class EuCentralesServiceImplImpl extends CrudServiceImpl<EuCentrales, Integer> implements EuCentralesService {

	private EuCentralesRepository centralesRepository;
	protected EuCentralesServiceImplImpl(EuCentralesRepository centralesRepository) {
		super(centralesRepository);
		this.centralesRepository = centralesRepository;
	}

}
