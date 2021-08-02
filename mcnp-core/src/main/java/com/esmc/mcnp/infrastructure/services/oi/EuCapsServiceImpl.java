package com.esmc.mcnp.infrastructure.services.oi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.oi.EuCapsRepository;
import com.esmc.mcnp.domain.entity.oi.EuCaps;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euCapsService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCapsServiceImpl extends BaseServiceImpl<EuCaps, String> implements EuCapsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private EuCapsRepository capsRepo;

	@Override
	public EuCaps findByCodeMembreBenef(String codeMembre) {
		return capsRepo.findByCodeMembreBenef(codeMembre);
	}

	@Override
	protected BaseRepository<EuCaps, String> getRepository() {
		return capsRepo;
	}

	@Override
	public EuCaps findByEuMembre2_CodeMembre(String codeMembre) {
		return capsRepo.findByEuMembre2_CodeMembre(codeMembre);
	}

}
