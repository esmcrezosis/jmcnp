package com.esmc.mcnp.services.org;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.org.EuPrefecture;
import com.esmc.mcnp.repositories.org.EuPrefectureRepository;
import com.esmc.mcnp.repositories.base.BaseRepository;

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
