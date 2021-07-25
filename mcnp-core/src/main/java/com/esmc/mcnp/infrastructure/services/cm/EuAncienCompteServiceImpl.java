package com.esmc.mcnp.services.cm;

import com.esmc.mcnp.model.cm.EuAncienCompte;
import com.esmc.mcnp.repositories.cm.EuAncienCompteRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EuAncienCompteServiceImpl extends CrudServiceImpl<EuAncienCompte, String> implements EuAncienCompteService {

    private final EuAncienCompteRepository ancienCompteRepository;
    protected EuAncienCompteServiceImpl(EuAncienCompteRepository ancienCompteRepository) {
        super(ancienCompteRepository);
        this.ancienCompteRepository = ancienCompteRepository;
    }
}
