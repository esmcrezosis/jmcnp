package com.esmc.mcnp.infrastructure.services.obps;

import com.esmc.mcnp.dao.repository.obps.EuAncienCreditConsommerRepository;
import com.esmc.mcnp.domain.entity.obps.EuAncienCreditConsommer;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EuAncienCreditConsommerServiceImpl extends CrudServiceImpl<EuAncienCreditConsommer, Long> implements EuAncienCreditConsommerService {
    private final EuAncienCreditConsommerRepository ancCreditConsRepo;

    protected EuAncienCreditConsommerServiceImpl(EuAncienCreditConsommerRepository ancCreditConsRepo) {
        super(ancCreditConsRepo);
        this.ancCreditConsRepo = ancCreditConsRepo;
    }

    @Override
    public List<EuAncienCreditConsommer> findByIdCredit(Long idCredit) {
        return ancCreditConsRepo.findByIdCredit(idCredit);
    }

    @Override
    public Double sumByIdCredit(Long idCedit) {
        return ancCreditConsRepo.sumAncienCreditConsommerByIdCredit(idCedit);
    }
}
