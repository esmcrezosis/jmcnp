package com.esmc.mcnp.services.bc;

import com.esmc.mcnp.model.bc.EuCnnc;
import com.esmc.mcnp.model.cm.EuCompteCredit;
import com.esmc.mcnp.model.cm.EuCompteCreditTs;
import com.esmc.mcnp.services.base.BaseService;

import java.util.Map;

/**
 * Created by USER on 06/12/2016.
 */
public interface EuCnncService extends BaseService<EuCnnc, Long> {
	Map<String, Object> transfererCreditNonConsommer(EuCompteCredit cc);

	void saveCnnc(EuCompteCredit cc);

	void saveCnncTs(EuCompteCreditTs ts);
}
