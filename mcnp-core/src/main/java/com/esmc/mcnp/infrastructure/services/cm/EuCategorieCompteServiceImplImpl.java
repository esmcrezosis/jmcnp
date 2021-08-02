package com.esmc.mcnp.infrastructure.services.cm;

import com.esmc.mcnp.dao.repository.cm.EuCategorieCompteRepository;
import com.esmc.mcnp.domain.entity.cm.EuCategorieCompte;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class EuCategorieCompteServiceImplImpl extends CrudServiceImpl<EuCategorieCompte, String> implements EuCategorieCompteService {

    private EuCategorieCompteRepository repository;

    protected EuCategorieCompteServiceImplImpl(EuCategorieCompteRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
