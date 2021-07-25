package com.esmc.mcnp.services.smcipn;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.smcipn.EuSmc;
import com.esmc.mcnp.model.smcipn.EuUtiliser;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.smcipn.EuUtiliserRepository;

@Service("euUtiliserService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuUtiliserServiceImpl extends BaseServiceImpl<EuUtiliser, Long> implements EuUtiliserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuUtiliserRepository utiliserRepo;

	@Override
	protected BaseRepository<EuUtiliser, Long> getRepository() {
		return utiliserRepo;
	}

	@Override
	public Long findLastUtiliserInsertedId() {
		return utiliserRepo.findLastUtiliserInsertedId();
	}

	@Override
	public List<EuSmc> findByEuSmcipnpwi_CodeSmcipn(String codeSmcipn) {
		return utiliserRepo.findByEuSmcipnpwi_CodeSmcipn(codeSmcipn);
	}

	@Override
	public List<EuUtiliser> findByIdCredit(Long idCredit) {
		return utiliserRepo.findByIdCredit(idCredit);
	}

}
