package com.esmc.mcnp.services.obps;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.obps.EuDetailCommande;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obps.EuDetailCommandeRepository;

@Service("euDetailCommandeService")
public class EuDetailCommandeServiceImpl extends BaseServiceImpl<EuDetailCommande, Long>
		implements EuDetailCommandeService {
	private static final long serialVersionUID = 1L;
	private @Autowired EuDetailCommandeRepository euDetailCommandeRepo;

	@Override
	protected BaseRepository<EuDetailCommande, Long> getRepository() {
		return euDetailCommandeRepo;
	}

	@Override
	public List<EuDetailCommande> findDetailsCommandeByCodeCommande(Long codeCommande) {
		return euDetailCommandeRepo.getListDetailCommande(codeCommande);
	}

}
