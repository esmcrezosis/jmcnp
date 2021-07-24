package com.esmc.mcnp.services.cm;

import com.esmc.mcnp.model.cm.EuCategorieCompte;
import com.esmc.mcnp.repositories.cm.EuCategorieCompteRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EuCategorieCompteServiceImplImpl extends CrudServiceImpl<EuCategorieCompte, String> implements EuCategorieCompteService {

    private EuCategorieCompteRepository repository;

    protected EuCategorieCompteServiceImplImpl(EuCategorieCompteRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
