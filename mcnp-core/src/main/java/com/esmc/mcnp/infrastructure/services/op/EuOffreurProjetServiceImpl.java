package com.esmc.mcnp.services.op;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.op.EuOffreurProjet;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.op.EuOffreurProjetRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuOffreurProjetServiceImpl extends BaseServiceImpl<EuOffreurProjet, Integer>
		implements EuOffreurProjetService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuOffreurProjetRepository ofProjetRepo;

	@Override
	protected BaseRepository<EuOffreurProjet, Integer> getRepository() {
		return ofProjetRepo;
	}

	@Override
	public Page<EuOffreurProjet> findAll(Pageable pageable) {
		return ofProjetRepo.findAll(pageable);
	}

	@Override
	public Page<EuOffreurProjet> findByMembre(String codeMembre, Pageable pageable) {
		return ofProjetRepo.findByCodeMembreContaining(codeMembre, pageable);
	}

	@Override
	public Page<EuOffreurProjet> findByDescription(String description, Pageable pageable) {
		return ofProjetRepo.findByDescriptionProjetContaining(description, pageable);
	}

	@Override
	public Page<EuOffreurProjet> findByProduit(String produit, Pageable pageable) {
		return ofProjetRepo.findByProduitContaining(produit, pageable);
	}

	@Override
	public Page<EuOffreurProjet> findByProduitOrDescription(String produit, String description, Pageable pageable) {
		return ofProjetRepo.findByProduitOrDescription(produit, description, pageable);
	}

}
