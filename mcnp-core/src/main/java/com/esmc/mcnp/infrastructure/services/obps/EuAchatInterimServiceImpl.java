package com.esmc.mcnp.services.obps;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.others.EuAchatInterim;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obps.EuAchatInterimRepository;

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
