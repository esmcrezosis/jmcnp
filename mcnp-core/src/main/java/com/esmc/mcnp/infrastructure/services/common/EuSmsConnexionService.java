package com.esmc.mcnp.infrastructure.services.common;

import com.esmc.mcnp.domain.entity.sms.EuSmsConnexion;
import com.esmc.mcnp.infrastructure.services.base.BaseService;

public interface EuSmsConnexionService extends BaseService<EuSmsConnexion, Long> {
	public Long getLastInsertId();
	
	public EuSmsConnexion getSmsConnexionByCodeEnvoi(String codeEnvoi);
	
	public String getCodeRecuSmsConnexionByCodeEnvoi(String codeMembre, String codeEnvoi);
}
