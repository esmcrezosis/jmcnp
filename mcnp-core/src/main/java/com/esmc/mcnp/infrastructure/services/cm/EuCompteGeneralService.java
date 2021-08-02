package com.esmc.mcnp.infrastructure.services.cm;

import com.esmc.mcnp.domain.entity.cm.EuCompteGeneral;
import com.esmc.mcnp.domain.entity.cm.EuCompteGeneralPK;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuCompteGeneralService extends BaseService<EuCompteGeneral, EuCompteGeneralPK> {
	EuCompteGeneral findByPK(String codeCompte, String codeTypeCompte, String service);
	void saveCG(String codeCompte, String codeTypeCompte, String service, double montant);
}
