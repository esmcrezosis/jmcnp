package com.esmc.mcnp.services.obps;

import com.esmc.mcnp.model.obps.EuAncienCreditConsommer;
import com.esmc.mcnp.services.base.CrudService;

import java.util.List;

public interface EuAncienCreditConsommerService extends CrudService<EuAncienCreditConsommer, Long> {
    List<EuAncienCreditConsommer> findByIdCredit(Long idCredit);

    Double sumByIdCredit(Long idCedit);
}
