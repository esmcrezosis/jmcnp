package com.esmc.mcnp.infrastructure.services.obps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.obps.EuGcpPreleverRepository;
import com.esmc.mcnp.domain.entity.obps.EuGcpPrelever;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service("euGcpPreleverService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuGcpPreleverServiceImpl extends BaseServiceImpl<EuGcpPrelever, Long> implements EuGcpPreleverService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuGcpPreleverRepository gcpPreleverRepo;

	@Override
	protected BaseRepository<EuGcpPrelever, Long> getRepository() {
		return gcpPreleverRepo;
	}

	@Override
	public List<EuGcpPrelever> findByIdTpagcp(Long idtpagcp) {
		return gcpPreleverRepo.findByIdTpagcp(idtpagcp);
	}

	@Override
	public List<EuGcpPrelever> findGcpPreleverByIdTpagcp(Long idtpagcp) {
		return gcpPreleverRepo.findGcpPreleverByIdTpagcp(idtpagcp);
	}

}
