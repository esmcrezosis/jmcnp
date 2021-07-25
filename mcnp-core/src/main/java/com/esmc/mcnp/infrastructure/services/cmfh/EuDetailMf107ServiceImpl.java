package com.esmc.mcnp.services.cmfh;

import com.esmc.mcnp.model.cmfh.EuDetailMf107;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.cmfh.EuDetailMf107Repository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EuDetailMf107ServiceImpl extends CrudServiceImpl<EuDetailMf107, Long> implements EuDetailMf107Service {
    private final EuDetailMf107Repository repository;

    protected EuDetailMf107ServiceImpl(EuDetailMf107Repository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Page<EuDetailMf107> findByCodeMembre(String codeMembre, Pageable pageable) {
        return null;
    }

    @Override
    public List<EuDetailMf107> findByIdMf107(Integer idMf107) {
        return repository.findByIdMf107(idMf107);
    }

    @Override
    public Page<EuDetailMf107> findByIdMf107(Integer idMf107, Pageable pageable) {
        return repository.findByIdMf107(idMf107, pageable);
    }

    @Override
    public List<EuDetailMf107> findByNumident(String numident) {
        return repository.findByNumident(numident);
    }

    @Override
    public Page<EuDetailMf107> findByNumident(String numident, Pageable pageable) {
        return repository.findByNumident(numident, pageable);
    }
}
