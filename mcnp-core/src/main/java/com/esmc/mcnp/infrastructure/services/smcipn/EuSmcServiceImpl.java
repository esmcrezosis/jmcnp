package com.esmc.mcnp.infrastructure.services.smcipn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuSmcRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuSmc;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euSmcService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuSmcServiceImpl extends BaseServiceImpl<EuSmc, Long> implements EuSmcService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private
    @Autowired
    EuSmcRepository smcRepo;

    @Override
    protected BaseRepository<EuSmc, Long> getRepository() {
        return smcRepo;
    }

    @Override
    public Long getLastEuSmcInsertedId() {
        return smcRepo.getLastEuSmcInsertedId();
    }

    @Override
    public List<EuSmc> findByOrigineSmc() {
        return smcRepo.findByOrigineSmc();
    }

    @Override
    public Double findSumByOrigineSmc() {
        return smcRepo.findSumByOrigineSmc();
    }

    @Override
    public List<EuSmc> findSmcByCodeSmcipn(String codeSmcipn) {
        return smcRepo.findSmcByCodeSmcipn(codeSmcipn);
    }

}
