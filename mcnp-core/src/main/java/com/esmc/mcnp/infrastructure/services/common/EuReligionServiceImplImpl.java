package com.esmc.mcnp.infrastructure.services.common;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.others.EuReligionRepository;
import com.esmc.mcnp.domain.entity.cm.EuReligion;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

@Service
public class EuReligionServiceImplImpl extends CrudServiceImpl<EuReligion, Integer> implements EuReligionService {

	private EuReligionRepository religionRepository;
	
	public EuReligionServiceImplImpl(EuReligionRepository religionRepository) {
		super(religionRepository);
		this.religionRepository = religionRepository;
	}

}
