package com.esmc.mcnp.services.obps;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.obps.EuCommande;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obps.EuCommandeRepository;

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
