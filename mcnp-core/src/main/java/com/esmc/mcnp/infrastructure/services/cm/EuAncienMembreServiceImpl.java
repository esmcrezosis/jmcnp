package com.esmc.mcnp.services.cm;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.cm.EuAncienMembre;
import com.esmc.mcnp.repositories.cm.EuAncienMembreRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuAncienMembreServiceImpl extends BaseServiceImpl<EuAncienMembre, String>
		implements EuAncienMembreService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuAncienMembreRepository ancMembreRepo;

	@Override
	protected BaseRepository<EuAncienMembre, String> getRepository() {
		return ancMembreRepo;
	}

	@Override
	public EuAncienMembre findByMembre(String codeMembre) {
		return ancMembreRepo.findByCodeMembre(codeMembre);
	}

	@Override
	public String getAncienCodeByMembre(String codeMembre) {
		return ancMembreRepo.getAncienCodeByCodeMembre(codeMembre);
	}
}
