package com.esmc.mcnp.services.acteurs;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.acteur.EuDetailContratLivraisonIrrevocable;
import com.esmc.mcnp.repositories.acteurs.EuDetailContratLivraisonIrrevocableRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 * Created by USER on 13/03/2017.
 */
@Service("euDetailContratLivraisonIrrevocableService")
@Transactional(propagation = Propagation.REQUIRED)
public class EuDetailContratLivraisonIrrevocableServiceImpl
		extends BaseServiceImpl<EuDetailContratLivraisonIrrevocable, Long>
		implements EuDetailContratLivraisonIrrevocableService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EuDetailContratLivraisonIrrevocableRepository irrevocableRepository;

	@Autowired
	public EuDetailContratLivraisonIrrevocableServiceImpl(
			EuDetailContratLivraisonIrrevocableRepository irrevocableRepository) {
		this.irrevocableRepository = irrevocableRepository;
	}

	@Override
	protected BaseRepository<EuDetailContratLivraisonIrrevocable, Long> getRepository() {
		return irrevocableRepository;
	}

	@Override
	public List<EuDetailContratLivraisonIrrevocable> findByContrat(Integer idContrat) {
		return irrevocableRepository.findByContrat_IdContrat(idContrat);
	}

	@Override
	public Double getSumMontant(Integer idContrat) {
		return irrevocableRepository.getSumByIdContrat(idContrat).orElse(0.0);
	}
}
