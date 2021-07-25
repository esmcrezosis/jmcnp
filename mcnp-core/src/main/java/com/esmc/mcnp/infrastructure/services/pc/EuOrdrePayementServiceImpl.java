package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.pc.EuOrdrePayement;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.pc.EuOrdrePayementRepository;

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
