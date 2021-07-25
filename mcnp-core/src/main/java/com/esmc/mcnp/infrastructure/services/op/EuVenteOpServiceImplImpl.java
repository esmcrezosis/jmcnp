package com.esmc.mcnp.services.op;

import com.esmc.mcnp.model.op.EuVenteOp;
import com.esmc.mcnp.repositories.op.EuVenteOpRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EuVenteOpServiceImplImpl extends CrudServiceImpl<EuVenteOp, Integer> implements EuVenteOpService {

    private EuVenteOpRepository venteOpRepository;

    protected EuVenteOpServiceImplImpl(EuVenteOpRepository venteOpRepository) {
        super(venteOpRepository);
        this.venteOpRepository = venteOpRepository;
    }
}
