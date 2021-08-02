package com.esmc.mcnp.infrastructure.services.op;

import com.esmc.mcnp.dao.repository.op.EuVenteOpRepository;
import com.esmc.mcnp.domain.entity.op.EuVenteOp;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class EuVenteOpServiceImplImpl extends CrudServiceImpl<EuVenteOp, Integer> implements EuVenteOpService {

    private EuVenteOpRepository venteOpRepository;

    protected EuVenteOpServiceImplImpl(EuVenteOpRepository venteOpRepository) {
        super(venteOpRepository);
        this.venteOpRepository = venteOpRepository;
    }
}
