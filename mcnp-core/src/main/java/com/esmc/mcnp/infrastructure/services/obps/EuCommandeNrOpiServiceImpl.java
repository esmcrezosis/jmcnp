package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuCommandeNrOpiRepository;
import com.esmc.mcnp.domain.entity.obps.EuCommandeNrOpi;
import com.esmc.mcnp.domain.entity.obpsd.EuTraite;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euCommandeNrOpiService")
public class EuCommandeNrOpiServiceImpl extends BaseServiceImpl<EuCommandeNrOpi, Integer> implements EuCommandeNrOpiService{
	
	private static final long serialVersionUID = 1L;
	private @Autowired EuCommandeNrOpiRepository euCommandeNrOpiRepo;

	@Override
	protected BaseRepository<EuCommandeNrOpi, Integer> getRepository() {
			return euCommandeNrOpiRepo;
	}

	@Override
	public List<EuTraite> findTraiteByCodeCommande(String codeCommande) {
		return euCommandeNrOpiRepo.findTraiteByCodeCommande(codeCommande);
	}

	
	
	
}
