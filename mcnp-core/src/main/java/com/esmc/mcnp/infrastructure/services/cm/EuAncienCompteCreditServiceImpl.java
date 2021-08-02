package com.esmc.mcnp.infrastructure.services.cm;

import com.esmc.mcnp.dao.repository.cm.EuAncienCompteCreditRepository;
import com.esmc.mcnp.domain.entity.cm.EuAncienCompteCredit;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EuAncienCompteCreditServiceImpl extends CrudServiceImpl<EuAncienCompteCredit, Long> implements EuAncienCompteCreditService {

    private final EuAncienCompteCreditRepository ancCompteCreditRepo;

    protected EuAncienCompteCreditServiceImpl(EuAncienCompteCreditRepository ancCompteCreditRepo) {
        super(ancCompteCreditRepo);
        this.ancCompteCreditRepo = ancCompteCreditRepo;
    }
}
