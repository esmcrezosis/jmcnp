package com.esmc.mcnp.infrastructure.services.obps;

import com.esmc.mcnp.domain.entity.obps.EuAncienCreditConsommer;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import java.util.List;

public interface EuAncienCreditConsommerService extends CrudService<EuAncienCreditConsommer, Long> {
    List<EuAncienCreditConsommer> findByIdCredit(Long idCredit);

    Double sumByIdCredit(Long idCedit);
}
