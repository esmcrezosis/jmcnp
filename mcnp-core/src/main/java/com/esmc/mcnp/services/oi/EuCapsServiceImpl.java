package com.esmc.mcnp.services.oi;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.oi.EuCaps;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.oi.EuCapsRepository;

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
