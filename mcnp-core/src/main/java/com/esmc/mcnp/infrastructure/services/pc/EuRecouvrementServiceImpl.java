package com.esmc.mcnp.infrastructure.services.pc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.pc.EuRecouvrementRepository;
import com.esmc.mcnp.domain.entity.pc.EuRecouvrement;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
