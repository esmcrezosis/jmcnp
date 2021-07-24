package com.esmc.mcnp.services.old;

import com.esmc.mcnp.model.old.Physique;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.old.PhysiqueRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
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
