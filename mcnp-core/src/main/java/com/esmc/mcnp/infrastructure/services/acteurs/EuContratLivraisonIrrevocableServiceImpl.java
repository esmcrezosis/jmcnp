package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.acteur.EuContratLivraisonIrrevocable;
import com.esmc.mcnp.repositories.acteurs.EuContratLivraisonIrrevocableRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 * Created by USER on 12/03/2017.
 */
@Service("euContratLivraisonIrrevocableService")
@Transactional(propagation = Propagation.REQUIRED)
public class EuContratLivraisonIrrevocableServiceImpl extends BaseServiceImpl<EuContratLivraisonIrrevocable, Integer> implements EuContratLivraisonIrrevocableService {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired
    EuContratLivraisonIrrevocableRepository contratRepo;
    @Override
    protected BaseRepository<EuContratLivraisonIrrevocable, Integer> getRepository() {
        return contratRepo;
    }
}
