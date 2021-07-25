package com.esmc.mcnp.services.obps;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.obps.EuCommandeNrOpi;
import com.esmc.mcnp.model.obpsd.EuTraite;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obps.EuCommandeNrOpiRepository;

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
