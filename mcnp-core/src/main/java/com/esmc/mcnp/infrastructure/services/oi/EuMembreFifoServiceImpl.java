package com.esmc.mcnp.infrastructure.services.oi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.dao.repository.oi.EuMembreFifoRepository;
import com.esmc.mcnp.domain.entity.odd.EuMembreFifo;
import com.esmc.mcnp.infrastructure.services.base.BaseServiceImpl;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuMembreFifoServiceImpl extends BaseServiceImpl<EuMembreFifo, Long> implements EuMembreFifoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuMembreFifoRepository membreFifoRepository;

	public EuMembreFifoServiceImpl() {
	}

	@Override
	public List<EuMembreFifo> findByNonAffecter(Integer valider, Integer substituer, Integer affecter) {
		return membreFifoRepository.findByValiderAndSubstituerAndAffecter(valider, substituer, affecter);
	}

	@Override
	protected BaseRepository<EuMembreFifo, Long> getRepository() {
		return membreFifoRepository;
	}

	@Override
	public List<EuMembreFifo> findByNonAffecter(Integer valider, Integer substituer, List<Integer> affecter) {
		return membreFifoRepository.findMmebreNonAffecter(valider, substituer, affecter);
	}

}
