package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuAncienGcpRepository;
import com.esmc.mcnp.domain.entity.obps.EuAncienGcp;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euAncienGcpService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuAncienGcpServiceImpl extends BaseServiceImpl<EuAncienGcp, Long> implements EuAncienGcpService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuAncienGcpRepository ancGcpRepo;

	@Override
	protected BaseRepository<EuAncienGcp, Long> getRepository() {
		return ancGcpRepo;
	}

	@Override
	public List<EuAncienGcp> findByMembre(String codeMembre) {
		return ancGcpRepo.findByCodeMembre(codeMembre);
	}

	@Override
	public Double getSoldeGcpByMembre(String codeMembre) {
		return ancGcpRepo.getSumResteByCodeMembre(codeMembre);
	}

}
