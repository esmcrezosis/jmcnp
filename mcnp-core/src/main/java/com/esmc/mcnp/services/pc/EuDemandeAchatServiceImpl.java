package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.others.EuDemandeAchat;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.pc.EuDemandeAchatRepository;

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
