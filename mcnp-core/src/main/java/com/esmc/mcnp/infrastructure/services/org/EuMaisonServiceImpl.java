package com.esmc.mcnp.infrastructure.services.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.org.EuMaisonRepository;
import com.esmc.mcnp.domain.entity.acteur.EuMaison;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;




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


