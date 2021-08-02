package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuAnnulationCommandeRepository;
import com.esmc.mcnp.domain.entity.obps.EuAnnulationCommande;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
