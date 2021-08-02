package com.esmc.mcnp.infrastructure.services.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.common.EuSmsConnexionRepository;
import com.esmc.mcnp.domain.entity.sms.EuSmsConnexion;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
