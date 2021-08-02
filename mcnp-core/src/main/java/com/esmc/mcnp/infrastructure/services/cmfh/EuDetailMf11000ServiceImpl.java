package com.esmc.mcnp.infrastructure.services.cmfh;

import com.esmc.mcnp.dao.repository.cmfh.EuDetailMf11000Repository;
import com.esmc.mcnp.domain.entity.cmfh.EuDetailMf11000;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EuDetailMf11000ServiceImpl extends CrudServiceImpl<EuDetailMf11000, Long> implements EuDetailMf11000Service {
    private final EuDetailMf11000Repository repository;

    public EuDetailMf11000ServiceImpl(EuDetailMf11000Repository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<EuDetailMf11000> findByMembre(String codeMembre) {
        return repository.findByCodeMembre(codeMembre);
    }

    @Override
    public Page<EuDetailMf11000> findByMembre(String codeMembre, Pageable pageable) {
        return repository.findByCodeMembre(codeMembre, pageable);
    }

    @Override
    public List<EuDetailMf11000> findById(Long idMf11000) {
        return repository.findByIdMf11000(idMf11000);
    }

    @Override
    public Page<EuDetailMf11000> findById(Long idMf11000, Pageable pageable) {
        return repository.findByIdMf11000(idMf11000, pageable);
    }

    @Override
    public EuDetailMf11000 findByMembreAndProprietaire(String codeMembre, Integer prop) {
        return repository.findByCodeMembreAndProprietaire(codeMembre, prop);
    }
}
