package com.esmc.mcnp.services.obpsd;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuEscompte;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obpsd.EuEscompteRepository;

@Service("euEscompteService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuEscompteServiceImpl extends BaseServiceImpl<EuEscompte, Long> implements EuEscompteService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuEscompteRepository escompteRepo;

	@Override
	protected BaseRepository<EuEscompte, Long> getRepository() {
		return escompteRepo;
	}

	@Override
	public Long getLastInsertedId() {
		return escompteRepo.getLastInsertedId();
	}

	@Override
	public List<EuEscompte> findAllEscompte() {
		return escompteRepo.findAllEscompte();
	}

	@Override
	public List<EuEscompte> findEscompteByMembre(String codeMembre) {
		return escompteRepo.findEscompteByMembre(codeMembre);
	}

	@Override
	public Double getSommeEscompteByCompte(String codeMembre) {
		return null;
	}

	@Override
	public List<EuEscompte> findEscompteEchuByCompte(String codeMembre) {
		return escompteRepo.findEscompteEchuByCompte(codeMembre);
	}

	@Override
	public Double getSommeEscompteEchuByCompte(String codeMembre) {
		return escompteRepo.getSommeEscompteByCompte(codeMembre);
	}

}
