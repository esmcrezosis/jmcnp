package com.esmc.mcnp.infrastructure.services.obps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuAchatInterimRepository;
import com.esmc.mcnp.domain.entity.others.EuAchatInterim;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euAchatInterimService")
@Transactional(propagation = Propagation.REQUIRED)
public class EuAchatInterimServiceImpl extends BaseServiceImpl<EuAchatInterim, Long> implements EuAchatInterimService {

	private static final long serialVersionUID = 1L;

	private @Autowired EuAchatInterimRepository achatInterimRepository;

	@Override
	protected BaseRepository<EuAchatInterim, Long> getRepository() {
		return achatInterimRepository;
	}

	@Override
	public EuAchatInterim findAchatInterimByCodeAchat(String codeAchat) {
		// TODO Auto-generated method stub
		return achatInterimRepository.findByCodeAchat(codeAchat);
	}

}
