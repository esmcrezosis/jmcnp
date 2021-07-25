package com.esmc.mcnp.services.cm;

import com.esmc.mcnp.model.bc.EuProduit;
import com.esmc.mcnp.repositories.cm.EuProduitRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EuProduitServiceImplImpl extends CrudServiceImpl<EuProduit, String> implements EuProduitService {

    private EuProduitRepository produitRepository;

    protected EuProduitServiceImplImpl(EuProduitRepository produitRepository) {
        super(produitRepository);
        this.produitRepository = produitRepository;
    }
}
