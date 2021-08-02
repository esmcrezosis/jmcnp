package com.esmc.mcnp.infrastructure.services.pc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.pc.EuOrdrePayementRepository;
import com.esmc.mcnp.domain.entity.pc.EuOrdrePayement;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("ordreService")
@Transactional
public class EuOrdrePayementServiceImpl extends BaseServiceImpl<EuOrdrePayement, Long>
		implements EuOrdrePayementService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuOrdrePayementRepository ordreRepo;

	@Override
	protected BaseRepository<EuOrdrePayement, Long> getRepository() {
		return ordreRepo;
	}

}
