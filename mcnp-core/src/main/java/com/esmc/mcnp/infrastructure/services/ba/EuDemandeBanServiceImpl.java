package com.esmc.mcnp.services.ba;

import java.time.LocalDateTime;
import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuDemandeBan;
import com.esmc.mcnp.repositories.ba.EuDemandeBanRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuDemandeBanServiceImpl extends BaseServiceImpl<EuDemandeBan, Integer> implements EuDemandeBanService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuDemandeBanRepository demBanRepo;

	@Override
	protected BaseRepository<EuDemandeBan, Integer> getRepository() {
		return demBanRepo;
	}

	@Override
	public List<EuDemandeBan> findByMembreAndValider(String codeMembre, boolean valider) {
		return demBanRepo.findByCodeMembreAndValider(codeMembre, valider);
	}

	@Override
	public List<EuDemandeBan> findByMembre(String codeMembre) {
		return demBanRepo.findByCodeMembre(codeMembre);
	}

	@Override
	public List<EuDemandeBan> findByDateDemande(LocalDateTime date) {
		return demBanRepo.findByDateDemande(date);
	}

}
