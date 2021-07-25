package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.acteur.EuLiasonCompte;
import com.esmc.mcnp.repositories.acteurs.EuLiasonCompteRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuLiasonCompteServiceImpl extends BaseServiceImpl<EuLiasonCompte, Integer>
		implements EuLiasonCompteService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuLiasonCompteRepository liasonRepo;

	public EuLiasonCompteServiceImpl() {
	}

	@Override
	public EuLiasonCompte findByCompteAdmin(String codeCompte) {
		return liasonRepo.findByCodeCompteAdmin(codeCompte);
	}

	@Override
	protected BaseRepository<EuLiasonCompte, Integer> getRepository() {
		return liasonRepo;
	}

	@Override
	public EuLiasonCompte findByCompteAnim(String codeCompte) {
		return liasonRepo.findByCodeCompteAnim(codeCompte);
	}

}
