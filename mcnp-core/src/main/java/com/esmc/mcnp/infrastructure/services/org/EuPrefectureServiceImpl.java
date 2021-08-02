package com.esmc.mcnp.infrastructure.services.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.org.EuPrefectureRepository;
import com.esmc.mcnp.domain.entity.org.EuPrefecture;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euPrefectureService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuPrefectureServiceImpl extends BaseServiceImpl<EuPrefecture, Integer> implements EuPrefectureService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuPrefectureRepository prefectureRepo;

	@Override
	protected BaseRepository<EuPrefecture, Integer> getRepository() {
		return prefectureRepo;
	}

}
