package com.esmc.mcnp.infrastructure.services.cm;

import com.esmc.mcnp.dao.repository.cm.EuAncienCompteRepository;
import com.esmc.mcnp.domain.entity.cm.EuAncienCompte;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

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
