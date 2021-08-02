package com.esmc.mcnp.infrastructure.services.old;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.old.PhysiqueRepository;
import com.esmc.mcnp.domain.entity.old.Physique;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PhysiqueServiceImpl extends CrudServiceImpl<Physique, String> implements PhysiqueService {
    private final PhysiqueRepository repository;

    protected PhysiqueServiceImpl(PhysiqueRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Physique findByCodeMembre(String codeMembre) {
        return repository.findByCodeMembre(codeMembre);
    }
}
