package com.esmc.mcnp.infrastructure.services.obps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuDetailCommandeAdditifRepository;
import com.esmc.mcnp.domain.entity.obps.EuDetailCommandeAdditif;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euDetailCommandeAdditifService")
public class EuDetailCommandeAdditifServiceImpl extends BaseServiceImpl<EuDetailCommandeAdditif, Long>
		implements EuDetailCommandeAdditifService {
	private static final long serialVersionUID = 1L;
	private @Autowired EuDetailCommandeAdditifRepository euDetailCommandeAdditifRepo;

	@Override
	protected BaseRepository<EuDetailCommandeAdditif, Long> getRepository() {
		return euDetailCommandeAdditifRepo;
	}

}
