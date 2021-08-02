package com.esmc.mcnp.infrastructure.services.cm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.others.EuRepresentationRepository;
import com.esmc.mcnp.domain.entity.cm.EuRepresentation;
import com.esmc.mcnp.domain.entity.cm.EuRepresentationPK;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euRepresentationService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuRepresentationServiceImpl extends BaseServiceImpl<EuRepresentation, EuRepresentationPK> implements EuRepresentationService {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuRepresentationRepository representantRepo;
	
	@Override
	protected BaseRepository<EuRepresentation, EuRepresentationPK> getRepository() {
		return representantRepo;
	}

	@Override
	public EuRepresentation getRepresentantPrincipal(String codeMembreMorale) {
		// TODO Auto-generated method stub
		return representantRepo.findCodeMembreRepresentantPrincipal(codeMembreMorale);
	}

	@Override
	public EuRepresentation findByMembreAndMorale(String morale, String membre) {
		return representantRepo.findByEuMembreMorale_CodeMembreMoraleAndEuMembre_CodeMembre(morale, membre);
	}

	@Override
	public EuRepresentation findByMoraleAndEtat(String morale, String etat) {
		return representantRepo.findByEuMembreMorale_CodeMembreMoraleAndEtat(morale, etat);
	}

	

}
