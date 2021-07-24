package com.esmc.mcnp.services.mprg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.mprg.EuKrr;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.mprg.EuKrrRepository;
import com.esmc.mcnp.services.base.BaseServiceImpl;

@Service("euKrrService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuKrrServiceImpl extends BaseServiceImpl<EuKrr, Long> implements EuKrrService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuKrrRepository krrRepo;

	@Override
	public EuKrr findByIdCreditAndTypeKrr(long idCredit, String typeKrr) {
		return krrRepo.findByIdCreditAndTypeKrr(idCredit, typeKrr);
	}

	@Override
	protected BaseRepository<EuKrr, Long> getRepository() {
		return krrRepo;
	}

}
