package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.model.pc.EuRecouvrementMcnp;
import com.esmc.mcnp.model.pc.EuReleve;
import com.esmc.mcnp.services.base.BaseService;

public interface EuRecouvrementMcnpService extends BaseService<EuRecouvrementMcnp, Integer> {

    String recouvrerMcnp(EuReleve releve);

    String recouvrerMcnp(Integer idReleve);

    String recouvrerMcnp(String codeMembre, String newCodeMembre, String type);
}
