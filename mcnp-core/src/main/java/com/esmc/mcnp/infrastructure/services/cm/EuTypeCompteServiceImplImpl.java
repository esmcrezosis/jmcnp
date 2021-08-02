package com.esmc.mcnp.infrastructure.services.cm;

import com.esmc.mcnp.dao.repository.cm.EuTypeCompteRepository;
import com.esmc.mcnp.domain.entity.cm.EuTypeCompte;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class EuTypeCompteServiceImplImpl extends CrudServiceImpl<EuTypeCompte, String> implements EuTypeCompteService {

    private EuTypeCompteRepository repository;

    protected EuTypeCompteServiceImplImpl(EuTypeCompteRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
