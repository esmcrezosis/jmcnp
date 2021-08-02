package com.esmc.mcnp.infrastructure.services.ot;

import com.esmc.mcnp.dao.repository.ot.EuCandidaturePosteRepository;
import com.esmc.mcnp.domain.entity.ot.EuCandidaturePoste;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EuCandidaturePosteServiceImpl extends CrudServiceImpl<EuCandidaturePoste, Integer> implements EuCandidaturePosteService {

    private final EuCandidaturePosteRepository repository;

    protected EuCandidaturePosteServiceImpl(EuCandidaturePosteRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<EuCandidaturePoste> findByCodePoste(String codePoste) {
        return repository.findByPoste_CodeRoles(codePoste);
    }

    @Override
    public Page<EuCandidaturePoste> findByCodePoste(String codePoste, Pageable page) {
        return repository.findByPoste_CodeRoles(codePoste, page);
    }

    @Override
    public List<EuCandidaturePoste> findByLibellePoste(String libelle) {
        return repository.findByPoste_LibelleRolesLike(libelle);
    }

    @Override
    public Page<EuCandidaturePoste> findByLibellePoste(String codePoste, Pageable page) {
        return repository.findByPoste_LibelleRolesLike(codePoste, page);
    }

    @Override
    public List<EuCandidaturePoste> findByCandidature(Integer idCandidature) {
        return repository.findByCandidature_IdCandidature(idCandidature);
    }

    @Override
    public Page<EuCandidaturePoste> findByCandidature(Integer idCandidature, Pageable pageable) {
        return repository.findByCandidature_IdCandidature(idCandidature, pageable);
    }

	@Override
	public EuCandidaturePoste findById(Integer id) {
		return repository.findByidCandidaturePoste(id);
	}
}
