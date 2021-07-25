package com.esmc.mcnp.services.obpsd;

import java.util.List;

import com.esmc.mcnp.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.obpsd.EuReglementWari;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.obpsd.EuReglementWariRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuReglementWariServiceImpl extends BaseServiceImpl<EuReglementWari, Long>
		implements EuReglementWariService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private @Autowired EuReglementWariRepository regWariRepo;

	@Override
	protected BaseRepository<EuReglementWari, Long> getRepository() {
		return regWariRepo;
	}

	@Override
	public List<EuReglementWari> findByNumeroTransaction(String numero) {
		return regWariRepo.findByNumeroTransaction(numero);
	}

	@Override
	public List<EuReglementWari> findByNumeroTraite(String numero) {
		return null;
	}

}
