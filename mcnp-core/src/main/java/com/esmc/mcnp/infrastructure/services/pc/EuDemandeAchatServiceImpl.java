package com.esmc.mcnp.infrastructure.services.pc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.pc.EuDemandeAchatRepository;
import com.esmc.mcnp.domain.entity.others.EuDemandeAchat;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuDemandeAchatServiceImpl extends BaseServiceImpl<EuDemandeAchat, Long> implements EuDemandeAchatService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EuDemandeAchatRepository demAchatRepo;

	@Override
	protected BaseRepository<EuDemandeAchat, Long> getRepository() {
		return demAchatRepo;
	}

}
