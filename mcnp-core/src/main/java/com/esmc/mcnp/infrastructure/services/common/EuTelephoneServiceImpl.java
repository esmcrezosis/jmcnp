package com.esmc.mcnp.infrastructure.services.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.others.EuTelephoneRepository;
import com.esmc.mcnp.domain.entity.cm.EuTelephone;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
