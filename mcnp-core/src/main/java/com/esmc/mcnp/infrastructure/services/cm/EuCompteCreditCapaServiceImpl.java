package com.esmc.mcnp.infrastructure.services.cm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.cm.EuCompteCreditCapaRepository;
import com.esmc.mcnp.domain.entity.cm.EuCompteCreditCapa;
import com.esmc.mcnp.domain.entity.cm.EuCompteCreditCapaPK;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euCompteCreditCapaService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCompteCreditCapaServiceImpl extends BaseServiceImpl<EuCompteCreditCapa, EuCompteCreditCapaPK>
		implements EuCompteCreditCapaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuCompteCreditCapaRepository cccapaRepo;

	@Override
	protected BaseRepository<EuCompteCreditCapa, EuCompteCreditCapaPK> getRepository() {
		return cccapaRepo;
	}

	@Override
	public List<EuCompteCreditCapa> findCreditCapaByIdCredit(Long idCredit) {
		return cccapaRepo.findCreditCapaByIdCredit(idCredit);
	}

}
