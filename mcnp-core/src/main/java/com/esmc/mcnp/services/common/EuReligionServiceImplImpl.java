package com.esmc.mcnp.services.common;

import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.cm.EuReligion;
import com.esmc.mcnp.repositories.others.EuReligionRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

@Service
public class EuReligionServiceImplImpl extends CrudServiceImpl<EuReligion, Integer> implements EuReligionService {

	private EuReligionRepository religionRepository;
	
	public EuReligionServiceImplImpl(EuReligionRepository religionRepository) {
		super(religionRepository);
		this.religionRepository = religionRepository;
	}

}
