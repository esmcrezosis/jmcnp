package com.esmc.mcnp.services.cm;

import com.esmc.mcnp.model.cm.EuCompteGeneral;
import com.esmc.mcnp.model.cm.EuCompteGeneralPK;
import com.esmc.mcnp.services.base.BaseService;

public interface EuCompteGeneralService extends BaseService<EuCompteGeneral, EuCompteGeneralPK> {
	EuCompteGeneral findByPK(String codeCompte, String codeTypeCompte, String service);
	void saveCG(String codeCompte, String codeTypeCompte, String service, double montant);
}
