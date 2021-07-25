package com.esmc.mcnp.services.old;

import com.esmc.mcnp.model.old.Morale;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.old.MoraleRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MoraleServiceImpl extends CrudServiceImpl<Morale, String> implements MoraleService {
    private final MoraleRepository repository;

    protected MoraleServiceImpl(MoraleRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Morale findByCodeMembre(String codeMembre) {
        return repository.findByCodeMembre(codeMembre);
    }
}
