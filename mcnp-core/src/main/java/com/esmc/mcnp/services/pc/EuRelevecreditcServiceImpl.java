package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.pc.EuRelevecreditc;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.pc.EuRelevecreditcRepository;

@Service("euRelevecredictService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuRelevecreditcServiceImpl extends BaseServiceImpl<EuRelevecreditc, Integer>
		implements EuRelevecreditcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuRelevecreditcRepository relcreditcRepo;
	@Override
	protected BaseRepository<EuRelevecreditc, Integer> getRepository() {
		return relcreditcRepo;
	}

}
