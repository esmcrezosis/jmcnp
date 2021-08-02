package com.esmc.mcnp.infrastructure.services.cmfh;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.cmfh.EuRepartitionMf107Repository;
import com.esmc.mcnp.domain.entity.cmfh.EuRepartitionMf107;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EuRepartitionMf107ServiceImpl extends CrudServiceImpl<EuRepartitionMf107, Long> implements EuRepartitionMf107Service {
    private final EuRepartitionMf107Repository repartitionMf107Repo;

    protected EuRepartitionMf107ServiceImpl(EuRepartitionMf107Repository repartitionMf107Repo) {
        super(repartitionMf107Repo);
        this.repartitionMf107Repo = repartitionMf107Repo;
    }

    @Override
    public List<EuRepartitionMf107> findByCodeMembre(String codeMembre) {
        return repartitionMf107Repo.findByCodeMembre(codeMembre);
    }

    @Override
    public List<EuRepartitionMf107> findByIdMf107(Long idMf107) {
        return repartitionMf107Repo.findByIdMf107(idMf107);
    }

    @Override
    public Page<EuRepartitionMf107> findByCodeMembre(String codeMembre, Pageable pageable) {
        return repartitionMf107Repo.findByCodeMembre(codeMembre, pageable);
    }

    @Override
    public Page<EuRepartitionMf107> findByIdMf107(Long idMf107, Pageable pageable) {
        return repartitionMf107Repo.findByIdMf107(idMf107, pageable);
    }
}
