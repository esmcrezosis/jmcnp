package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuBpsRepository;
import com.esmc.mcnp.domain.entity.obps.EuBps;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
