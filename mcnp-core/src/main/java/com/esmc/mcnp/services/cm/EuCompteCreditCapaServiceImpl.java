package com.esmc.mcnp.services.cm;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.cm.EuCompteCreditCapa;
import com.esmc.mcnp.model.cm.EuCompteCreditCapaPK;
import com.esmc.mcnp.repositories.cm.EuCompteCreditCapaRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

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
