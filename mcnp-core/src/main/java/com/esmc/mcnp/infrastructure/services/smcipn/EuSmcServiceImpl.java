package com.esmc.mcnp.services.smcipn;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.smcipn.EuSmc;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.smcipn.EuSmcRepository;

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
