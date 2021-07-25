package com.esmc.mcnp.services.common;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.sms.EuSmsConnexion;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.common.EuSmsConnexionRepository;

@Service("EuSmsConnexionService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuSmsConnexionServiceImpl extends BaseServiceImpl<EuSmsConnexion, Long> implements EuSmsConnexionService {
	private static final long serialVersionUID = 1L;
	private @Autowired EuSmsConnexionRepository euSmsConnexionRepo;
	@Override
	protected BaseRepository<EuSmsConnexion, Long> getRepository() {
		return euSmsConnexionRepo;
	}
	@Override
	public Long getLastInsertId() {
		return euSmsConnexionRepo.getLastInsertId();
	}    
	@Override
	public String getCodeRecuSmsConnexionByCodeEnvoi(String codeMembre, String codeEnvoi) {
		return euSmsConnexionRepo.getCodeRecuSmsConnexionByCodeEnvoi(codeMembre, codeEnvoi);
	}
	
	@Override
	public EuSmsConnexion getSmsConnexionByCodeEnvoi(String codeEnvoi) {
		return euSmsConnexionRepo.getSmsConnexionByCodeEnvoi(codeEnvoi);
	}
	
	
}
