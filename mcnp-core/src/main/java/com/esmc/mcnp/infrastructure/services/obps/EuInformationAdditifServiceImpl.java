package com.esmc.mcnp.services.obps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.others.EuInformationAdditif;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obps.EuInformationAdditifRepository;
import com.esmc.mcnp.services.base.BaseServiceImpl;

@Service("euInformationAdditifService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuInformationAdditifServiceImpl extends BaseServiceImpl<EuInformationAdditif, Long> implements EuInformationAdditifService {

	private static final long serialVersionUID = 1L;
	private @Autowired EuInformationAdditifRepository euInformationAdditifRepo;

	@Override
	protected BaseRepository<EuInformationAdditif, Long> getRepository() {
		return euInformationAdditifRepo;
	}

	@Override
	public List<EuInformationAdditif> retrouverInformationAd(String codeMembre,String reference) {
		return euInformationAdditifRepo.findInformationAdditifByMembre(codeMembre, reference);
	}

	
}
