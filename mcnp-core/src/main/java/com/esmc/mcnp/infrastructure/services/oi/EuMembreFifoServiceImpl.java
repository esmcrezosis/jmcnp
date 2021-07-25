package com.esmc.mcnp.services.oi;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.odd.EuMembreFifo;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.oi.EuMembreFifoRepository;

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
