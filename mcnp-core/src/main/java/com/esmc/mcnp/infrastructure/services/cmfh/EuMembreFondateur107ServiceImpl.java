package com.esmc.mcnp.infrastructure.services.cmfh;

import com.esmc.mcnp.dao.repository.cmfh.EuMembreFondateurMf107Repository;
import com.esmc.mcnp.domain.entity.cmfh.EuMembreFondateur107;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EuMembreFondateur107ServiceImpl extends CrudServiceImpl<EuMembreFondateur107, String> implements EuMembreFondateur107Service {
    private final EuMembreFondateurMf107Repository repository;

    protected EuMembreFondateur107ServiceImpl(EuMembreFondateurMf107Repository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public EuMembreFondateur107 findByMembre(String codeMembre) {
        return repository.findByCodeMembre(codeMembre);
    }

	@Override
	public Page<EuMembreFondateur107> findByNumident(String numident, Pageable pageable) {
		return repository.findByNumident(numident, pageable);
	}

	@Override
	public Page<EuMembreFondateur107> findByMembre(String codeMembre, Pageable pageable) {
		return repository.findByCodeMembre(codeMembre, pageable);
	}

	@Override
	public Page<EuMembreFondateur107> findByNom(String nom, Pageable pageable) {
		return repository.findByNom(nom, pageable);
	}

	@Override
	public Page<EuMembreFondateur107> findByNomAndPrenom(String nom, String prenom, Pageable pageable) {
		return repository.findByNomAndPrenom(nom, prenom, pageable);
	}
}
