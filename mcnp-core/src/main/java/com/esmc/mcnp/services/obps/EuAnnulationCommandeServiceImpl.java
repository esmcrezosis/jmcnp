package com.esmc.mcnp.services.obps;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obps.EuAnnulationCommande;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obps.EuAnnulationCommandeRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuAnnulationCommandeServiceImpl extends BaseServiceImpl<EuAnnulationCommande, Integer>
		implements EuAnnulationCommandeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuAnnulationCommandeRepository annulationCommandeRepo;

	@Override
	protected BaseRepository<EuAnnulationCommande, Integer> getRepository() {
		return annulationCommandeRepo;
	}

	@Override
	public List<EuAnnulationCommande> findByComande(Integer codeCommande) {
		return annulationCommandeRepo.findByCodeCommande(codeCommande);
	}

}
