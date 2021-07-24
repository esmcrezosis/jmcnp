package com.esmc.mcnp.services.obps;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obps.EuBps;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obps.EuBpsRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuBpsServiceImpl extends BaseServiceImpl<EuBps, Integer> implements EuBpsService {

	private static final long serialVersionUID = 1L;
	private @Autowired EuBpsRepository bpsRepo;

	@Override
	protected BaseRepository<EuBps, Integer> getRepository() {
		return bpsRepo;
	}

	@Override
	public List<EuBps> findAllBps() {

		return bpsRepo.findAll();
	}

	@Override
	public EuBps findByDesignationAndTypeSouscription(String designation, String typesouscription) {
		return bpsRepo.findByDesignationAndTypeSouscription(designation, typesouscription);
	}

	@Override
	public EuBps findByDesignation(String designation) {
		return bpsRepo.findByDesignation(designation);
	}

}
