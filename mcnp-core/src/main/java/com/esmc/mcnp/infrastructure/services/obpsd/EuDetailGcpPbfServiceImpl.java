package com.esmc.mcnp.services.obpsd;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuDetailGcpPbf;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obpsd.EuDetailGcpPbfRepository;

@Service("euDetailGcpPbfService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuDetailGcpPbfServiceImpl extends BaseServiceImpl<EuDetailGcpPbf, Long> implements EuDetailGcpPbfService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuDetailGcpPbfRepository detGcpPbfRepo;

	@Override
	public Long getLastInsertedId() {
		return detGcpPbfRepo.getLastInsertedId();
	}

	@Override
	public List<EuDetailGcpPbf> findByEuGcpPbf_CodeGcpPbf(String codeGcpPbf) {
		return detGcpPbfRepo.findByEuGcpPbf_CodeGcpPbf(codeGcpPbf);
	}

	@Override
	public List<EuDetailGcpPbf> findDetailByGcpPbfB(String codeGcpPbf) {
		return detGcpPbfRepo.findDetailByGcpPbfB(codeGcpPbf);
	}

	@Override
	public List<EuDetailGcpPbf> findDetailByEscompte(long id_escompte) {
		return detGcpPbfRepo.findDetailByEscompte(id_escompte);
	}

	@Override
	public List<EuDetailGcpPbf> findDetailByEchange(long id_echange) {
		return detGcpPbfRepo.findDetailByEchange(id_echange);
	}

	@Override
	protected BaseRepository<EuDetailGcpPbf, Long> getRepository() {
		return detGcpPbfRepo;
	}

}
