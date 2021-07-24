package com.esmc.mcnp.services.cm;

import com.esmc.mcnp.model.cm.EuTypeCompte;
import com.esmc.mcnp.repositories.cm.EuTypeCompteRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EuTypeCompteServiceImplImpl extends CrudServiceImpl<EuTypeCompte, String> implements EuTypeCompteService {

    private EuTypeCompteRepository repository;

    protected EuTypeCompteServiceImplImpl(EuTypeCompteRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
