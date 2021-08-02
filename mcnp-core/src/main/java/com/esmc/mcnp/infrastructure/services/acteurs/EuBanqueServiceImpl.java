package com.esmc.mcnp.infrastructure.services.acteurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.acteurs.EuBanqueRepository;
import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuBanque;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euBanqueService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuBanqueServiceImpl extends BaseServiceImpl<EuBanque, String> implements EuBanqueService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private @Autowired
    EuBanqueRepository banqueRepo;

    @Override
    protected BaseRepository<EuBanque, String> getRepository() {
        return banqueRepo;
    }

    @Override
    public EuBanque findByMembre(String codeMembre) {
        return banqueRepo.findByCodeMembreMorale(codeMembre);
    }

    @Override
    public EuBanque findByDefaut() {
        return banqueRepo.findByDefault();
    }

}
