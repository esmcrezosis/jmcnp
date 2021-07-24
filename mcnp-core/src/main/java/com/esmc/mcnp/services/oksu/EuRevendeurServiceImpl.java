package com.esmc.mcnp.services.oksu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.oksu.EuRevendeur;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.oksu.EuRevendeurRepository;
import com.esmc.mcnp.services.base.BaseServiceImpl;

@Service("euRevendeurService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuRevendeurServiceImpl extends BaseServiceImpl<EuRevendeur, Long> implements EuRevendeurService {

    private static final long serialVersionUID = 1L;
    private @Autowired
    EuRevendeurRepository revendeurRepo;

    @Override
    protected BaseRepository<EuRevendeur, Long> getRepository() {
        return revendeurRepo;
    }

    @Override
    public List<String> getListeProduitsRevendeur() {
        return revendeurRepo.getListeProduitsRevendeur();
    }

}
