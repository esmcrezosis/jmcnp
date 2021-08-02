package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuDetailCommandeRepository;
import com.esmc.mcnp.domain.entity.obps.EuDetailCommande;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
