package com.esmc.mcnp.infrastructure.services.cm;

import com.esmc.mcnp.dao.repository.cm.EuProduitRepository;
import com.esmc.mcnp.domain.entity.bc.EuProduit;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class EuProduitServiceImplImpl extends CrudServiceImpl<EuProduit, String> implements EuProduitService {

    private EuProduitRepository produitRepository;

    protected EuProduitServiceImplImpl(EuProduitRepository produitRepository) {
        super(produitRepository);
        this.produitRepository = produitRepository;
    }
}
