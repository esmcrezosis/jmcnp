package com.esmc.mcnp.infrastructure.services.smcipn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.smcipn.EuUtiliserRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuSmc;
import com.esmc.mcnp.domain.entity.smcipn.EuUtiliser;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

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
