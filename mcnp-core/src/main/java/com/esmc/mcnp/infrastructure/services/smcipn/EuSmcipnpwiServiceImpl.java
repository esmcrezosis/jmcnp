package com.esmc.mcnp.services.smcipn;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.smcipn.EuSmcipnpwi;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.smcipn.EuSmcipnwiRepository;

@Service("euSmcipnpwiService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuSmcipnpwiServiceImpl extends BaseServiceImpl<EuSmcipnpwi, String> implements EuSmcipnpwiService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private
    @Autowired
    EuSmcipnwiRepository smcipnRepo;

    @Override
    protected BaseRepository<EuSmcipnpwi, String> getRepository() {
        return smcipnRepo;
    }

    @Override
    public String findLastByCodeMembreAndTypeSmcipn(String codeMembre, String type) {
        return smcipnRepo.findLastByCodeMembreAndTypeSmcipn(codeMembre, type);
    }

    @Override
    public EuSmcipnpwi findByNumeroAppel(String numeroAppel) {
        return smcipnRepo.findByNumeroAppel(numeroAppel);
    }

}
