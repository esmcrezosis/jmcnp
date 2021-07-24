package com.esmc.mcnp.services.obps;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obps.EuAncienGcp;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obps.EuAncienGcpRepository;

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
