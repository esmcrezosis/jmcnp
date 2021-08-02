package com.esmc.mcnp.infrastructure.services.cmfh;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.cmfh.EuRepartitionMf11000Repository;
import com.esmc.mcnp.domain.entity.cmfh.EuRepartitionMf11000;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EuRepartitionMf11000ServiceImpl extends CrudServiceImpl<EuRepartitionMf11000, Long> implements EuRepartitionMf11000Service {
    private final EuRepartitionMf11000Repository repartitionMf11000Repo;

    protected EuRepartitionMf11000ServiceImpl(EuRepartitionMf11000Repository repartitionMf11000Repo) {
        super(repartitionMf11000Repo);
        this.repartitionMf11000Repo = repartitionMf11000Repo;
    }

    @Override
    public List<EuRepartitionMf11000> findByCodeMembre(String codeMembre) {
        return repartitionMf11000Repo.findByCodeMembre(codeMembre);
    }

    @Override
    public List<EuRepartitionMf11000> findByCodeMf11000(String codeMf11000) {
        return repartitionMf11000Repo.findByCodeMf11000(codeMf11000);
    }

    @Override
    public List<EuRepartitionMf11000> findByIdMf11000(Long idMf11000) {
        return repartitionMf11000Repo.findByIdMf11000(idMf11000);
    }

    @Override
    public Page<EuRepartitionMf11000> findByCodeMembre(String codeMembre, Pageable pageable) {
        return repartitionMf11000Repo.findByCodeMembre(codeMembre, pageable);
    }

    @Override
    public Page<EuRepartitionMf11000> findByCodeMf11000(String codeMf11000, Pageable pageable) {
        return repartitionMf11000Repo.findByCodeMf11000(codeMf11000, pageable);
    }

    @Override
    public Page<EuRepartitionMf11000> findByIdMf11000(Long idMf11000, Pageable pageable) {
        return repartitionMf11000Repo.findByIdMf11000(idMf11000, pageable);
    }
}
