package com.esmc.mcnp.services.cmfh;

import com.esmc.mcnp.services.base.BaseServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.acteur.EuAssociation;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.cmfh.EuAssociationRepository;

@Service("euAssociationService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED) 
public class EuAssociationServiceImpl extends BaseServiceImpl<EuAssociation, Long> implements EuAssociationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuAssociationRepository assoRepo;

	public EuAssociationServiceImpl() {
	}

	@Override
	protected BaseRepository<EuAssociation, Long> getRepository() {
		return assoRepo;
	}

	@Override
	public List<EuAssociation> findByAssociationNom(String associationNom) {
		return assoRepo.findByAssociationNomLike(associationNom);
	}

	@Override
	public EuAssociation findByCodeMembre(String codeMembre) {
		return assoRepo.findByCodeMembre(codeMembre);
	}

}
