package com.esmc.mcnp.infrastructure.services.pc;

import com.esmc.mcnp.domain.entity.pc.EuRecouvrementMcnp;
import com.esmc.mcnp.domain.entity.pc.EuReleve;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuRecouvrementMcnpService extends BaseService<EuRecouvrementMcnp, Integer> {

    String recouvrerMcnp(EuReleve releve);

    String recouvrerMcnp(Integer idReleve);

    String recouvrerMcnp(String codeMembre, String newCodeMembre, String type);
}
