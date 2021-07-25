package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuBanque;
import com.esmc.mcnp.repositories.acteurs.EuBanqueRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

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
