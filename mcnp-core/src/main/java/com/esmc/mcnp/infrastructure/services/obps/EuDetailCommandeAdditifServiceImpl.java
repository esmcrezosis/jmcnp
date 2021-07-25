package com.esmc.mcnp.services.obps;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.obps.EuDetailCommandeAdditif;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obps.EuDetailCommandeAdditifRepository;

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
