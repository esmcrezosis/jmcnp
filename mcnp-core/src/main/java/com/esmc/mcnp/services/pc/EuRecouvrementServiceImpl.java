package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.pc.EuRecouvrement;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.pc.EuRecouvrementRepository;

@Service("euRecouvrementService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuRecouvrementServiceImpl extends BaseServiceImpl<EuRecouvrement, Integer>
		implements EuRecouvrementService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EuRecouvrementRepository recouvrementService;

	@Override
	protected BaseRepository<EuRecouvrement, Integer> getRepository() {
		return recouvrementService;
	}

}
