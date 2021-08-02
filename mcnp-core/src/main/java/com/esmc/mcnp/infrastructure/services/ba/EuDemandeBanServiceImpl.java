package com.esmc.mcnp.infrastructure.services.ba;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.ba.EuDemandeBanRepository;
import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuDemandeBan;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
