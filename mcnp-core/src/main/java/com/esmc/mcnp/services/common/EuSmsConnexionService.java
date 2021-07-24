package com.esmc.mcnp.services.common;

import com.esmc.mcnp.model.sms.EuSmsConnexion;
import com.esmc.mcnp.services.base.BaseService;

public interface EuSmsConnexionService extends BaseService<EuSmsConnexion, Long> {
	public Long getLastInsertId();
	
	public EuSmsConnexion getSmsConnexionByCodeEnvoi(String codeEnvoi);
	
	public String getCodeRecuSmsConnexionByCodeEnvoi(String codeMembre, String codeEnvoi);
}
