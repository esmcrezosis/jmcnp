package com.esmc.mcnp.services.cmfh;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.cmfh.EuCmfh;
import com.esmc.mcnp.repositories.base.BaseRepository;
import com.esmc.mcnp.repositories.cmfh.EuCmfhRepository;
import com.esmc.mcnp.services.base.BaseServiceImpl;

@Service
@Transactional(readOnly = true)
public class EuCmfhServiceImpl extends BaseServiceImpl<EuCmfh, Long> implements EuCmfhService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EuCmfhRepository cmfhRepository;

	public EuCmfhServiceImpl(EuCmfhRepository cmfhRepository) {
		this.cmfhRepository = cmfhRepository;
	}

	@Override
	protected BaseRepository<EuCmfh, Long> getRepository() {
		return cmfhRepository;
	}

	@Override
	public EuCmfh findByMembre(String codeMembre) {
		return cmfhRepository.findByCodeMembre(codeMembre);
	}

	@Override
	public List<EuCmfh> findAllByMembre(String codeMembre) {
		return cmfhRepository.findAllByCodeMembre(codeMembre);
	}

	@Override
	public List<EuCmfh> findByRembourser(Integer rembourser) {
		return cmfhRepository.findByRembourser(rembourser);
	}

	@Override
	public EuCmfh findByMembreAndRembourser(String codeMembre, Integer rembourser) {
		return cmfhRepository.findByCodeMembreAndRembourser(codeMembre, rembourser);
	}

}
