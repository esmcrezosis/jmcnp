package com.esmc.mcnp.services.common;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.cm.EuTelephone;
import com.esmc.mcnp.repositories.others.EuTelephoneRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service("euTelephoneService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuTelephoneServiceImpl extends BaseServiceImpl<EuTelephone, Integer> implements EuTelephoneService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuTelephoneRepository telRepo;

	@Override
	public List<EuTelephone> findByMembre(String codeMembre) {
		return telRepo.findByCodeMembreLike(codeMembre);
	}

	@Override
	public List<EuTelephone> findByMembreAndCompagnie(String codeMembre, String compagnie) {
		return telRepo.findByCodeMembreLikeAndCompagnieTelephoneLike(codeMembre, compagnie);
	}

	@Override
	protected BaseRepository<EuTelephone, Integer> getRepository() {
		return telRepo;
	}

}
