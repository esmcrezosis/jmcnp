package com.esmc.mcnp.infrastructure.services.obps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuCommandeRepository;
import com.esmc.mcnp.domain.entity.obps.EuCommande;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euCommandeService")
public class EuCommandeServiceImpl extends BaseServiceImpl<EuCommande, Long> implements EuCommandeService {

	private static final long serialVersionUID = 1L;
	private @Autowired EuCommandeRepository euCommandeRepo;

	@Override
	protected BaseRepository<EuCommande, Long> getRepository() {
		return euCommandeRepo;
	}

	@Override
	public EuCommande findByCodeConfirmation(String codeConfirmation) {
		return euCommandeRepo.findBycodeConfirmation(codeConfirmation);
	}

}
