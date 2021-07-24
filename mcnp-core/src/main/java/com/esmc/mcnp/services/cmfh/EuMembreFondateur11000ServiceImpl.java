package com.esmc.mcnp.services.cmfh;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.cmfh.EuMembreFondateur11000;
import com.esmc.mcnp.repositories.cmfh.EuMembreFondateur11000Repository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

@Service("euMembreFondateur11000Service")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuMembreFondateur11000ServiceImpl extends CrudServiceImpl<EuMembreFondateur11000, Long>
		implements EuMembreFondateur11000Service {

	private EuMembreFondateur11000Repository mf11000Repo;

	protected EuMembreFondateur11000ServiceImpl(EuMembreFondateur11000Repository mf11000Repo) {
		super(mf11000Repo);
		this.mf11000Repo = mf11000Repo;
	}

	@Override
	public EuMembreFondateur11000 findByMembre(String codeMembre) {
		return mf11000Repo.findByCodeMembre(codeMembre);
	}

	@Override
	public Page<EuMembreFondateur11000> findByNumBon(Long numbon, Pageable pageable) {
		return mf11000Repo.findByNumBon(numbon, pageable);
	}

	@Override
	public Page<EuMembreFondateur11000> findByMembre(String codeMembre, Pageable pageable) {
		return mf11000Repo.findByCodeMembre(codeMembre, pageable);
	}

	@Override
	public Page<EuMembreFondateur11000> findByNomAndPrenom(String nom, String prenom, Pageable pageable) {
		return mf11000Repo.findByNomAndPrenom(nom, prenom, pageable);
	}

	@Override
	public Page<EuMembreFondateur11000> findByNom(String nom, Pageable pageable) {
		return mf11000Repo.findByNom(nom, pageable);
	}

}
