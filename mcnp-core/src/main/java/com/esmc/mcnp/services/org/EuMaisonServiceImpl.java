package com.esmc.mcnp.services.org;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.acteur.EuMaison;
import com.esmc.mcnp.repositories.org.EuMaisonRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;




@Service("euMaisonService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuMaisonServiceImpl extends BaseServiceImpl<EuMaison, Long> implements EuMaisonService {

	
    private static final long serialVersionUID = 1L;
	
	private @Autowired EuMaisonRepository maisonRepo;

	@Override
	protected BaseRepository<EuMaison, Long> getRepository() {
		return maisonRepo;
	}

	@Override
	public EuMaison findMaisonByCodeMembre(String codeMembre) {
		return maisonRepo.getMaisonByCodemembre(codeMembre);
	}





}


