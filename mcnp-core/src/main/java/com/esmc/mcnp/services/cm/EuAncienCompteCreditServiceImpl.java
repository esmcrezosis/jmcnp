package com.esmc.mcnp.services.cm;

import com.esmc.mcnp.model.cm.EuAncienCompteCredit;
import com.esmc.mcnp.repositories.cm.EuAncienCompteCreditRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
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
