package com.esmc.mcnp.infrastructure.services.pc;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.pc.EuCasPassifRepository;
import com.esmc.mcnp.domain.entity.pc.EuCasPassif;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EuCasPassifServiceImpl extends CrudServiceImpl<EuCasPassif, Integer> implements EuCasPassifService {

    private final EuCasPassifRepository repository;
    protected EuCasPassifServiceImpl(EuCasPassifRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<EuCasPassif> findByEntreprise(String entreprise) {
        return repository.findByEntreprise(entreprise);
    }
}
