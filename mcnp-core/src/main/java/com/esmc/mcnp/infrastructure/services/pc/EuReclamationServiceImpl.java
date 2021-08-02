package com.esmc.mcnp.infrastructure.services.pc;

import com.esmc.mcnp.dao.repository.pc.EuReclamationRepository;
import com.esmc.mcnp.domain.entity.pc.EuReclamation;
import com.esmc.mcnp.domain.entity.pc.TypePassif;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EuReclamationServiceImpl extends CrudServiceImpl<EuReclamation, Long> implements EuReclamationService{
    private final EuReclamationRepository repository;

    public EuReclamationServiceImpl(EuReclamationRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<EuReclamation> findByPassifAndCas(TypePassif passif, Integer idCasPassif) {
        return repository.findByPassifAndCas(passif, idCasPassif);
    }

    @Override
    public EuReclamation findByMembreAndPassifAndCas(String codeMembre, TypePassif passif, Integer idCasPassif) {
        return repository.findByMembreAndPassifAndCas(codeMembre, passif, idCasPassif);
    }

    @Override
    public List<EuReclamation> findByTypePassif(TypePassif typePassif) {
        return repository.findByTypePassif(typePassif);
    }

    @Override
    public List<EuReclamation> findByCasPassif(Integer idCas) {
        return repository.findByCasPassif_Id(idCas);
    }

    @Override
    public List<EuReclamation> findByCodeMembre(String codeMembre) {
        return repository.findByCodeMembre(codeMembre);
    }

    @Override
    public List<EuReclamation> findByMembreAndCasPassif(String codeMembre, Integer idCas) {
        return repository.findByCodeMembreAndCasPassif_Id(codeMembre, idCas);
    }

    @Override
    public Page<EuReclamation> findByMembreAndCasPassif(String codeMembre, Integer idCas, Pageable pageable) {
        return repository.findByCodeMembreAndCasPassif_Id(codeMembre, idCas, pageable);
    }

    @Override
    public List<EuReclamation> findByMembreAndTypePassif(String codeMembre, TypePassif typePassif) {
        return repository.findByCodeMembreAndTypePassif(codeMembre, typePassif);
    }

    @Override
    public Page<EuReclamation> findByMembreAndTypePassif(String codeMembre, TypePassif typePassif, Pageable pageable) {
        return repository.findByCodeMembreAndTypePassif(codeMembre, typePassif, pageable);
    }

    @Override
    public Page<EuReclamation> findByCodeMembre(String codeMembre, Pageable pageable) {
        return repository.findByCodeMembre(codeMembre, pageable);
    }

    @Override
    public Page<EuReclamation> findByTypePassif(TypePassif typePassif, Pageable pageable) {
        return repository.findByTypePassif(typePassif, pageable);
    }

    @Override
    public Page<EuReclamation> findByCasPassif(Integer idCas, Pageable pageable) {
        return repository.findByCasPassif_Id(idCas, pageable);
    }

    @Override
    public Page<EuReclamation> findByPassifAndCas(TypePassif passif, Integer idCasPassif, Pageable pageable) {
        return repository.findByPassifAndCas(passif, idCasPassif, pageable);
    }
}
