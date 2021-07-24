package com.esmc.mcnp.services.obpsd;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuGcpPbf;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obpsd.EuGcpPbfRepository;

@Service("euGcpPbfService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuGcpPbfServiceImpl extends BaseServiceImpl<EuGcpPbf, String> implements EuGcpPbfService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private @Autowired
    EuGcpPbfRepository gcpPbfRepo;

    @Override
    protected BaseRepository<EuGcpPbf, String> getRepository() {
        return gcpPbfRepo;
    }

    @Override
    public List<EuGcpPbf> findGcpPbfByCompte(String codeCompte) {
        return gcpPbfRepo.findGcpPbfByCompte(codeCompte);
    }

    @Override
    public EuGcpPbf findGcpPbfByCompteAndCapa(String codeCompte, String codeCapa) {
        return gcpPbfRepo.findGcpPbfByCompteAndCapa(codeCompte, codeCapa);
    }

    @Override
    public Double getSumGcpPbfByCompte(String codeCompte) {
        return gcpPbfRepo.getSumGcpPbfByCompte(codeCompte);
    }

    @Override
    public Double getSumGcpPbfByCompteCapa(String codeCompte, String typeCapa) {
        return gcpPbfRepo.getSumGcpPbfByCompteCapa(codeCompte, typeCapa);
    }

}
