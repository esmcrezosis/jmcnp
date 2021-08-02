package com.esmc.mcnp.infrastructure.services.acteurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.acteurs.EuContratLivraisonIrrevocableRepository;
import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.acteur.EuContratLivraisonIrrevocable;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
