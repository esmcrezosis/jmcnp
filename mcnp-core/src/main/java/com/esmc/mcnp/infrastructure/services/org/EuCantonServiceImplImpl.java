package com.esmc.mcnp.services.org;

import com.esmc.mcnp.dto.projections.CantonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.model.org.EuCanton;
import com.esmc.mcnp.repositories.org.EuCantonRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

import java.util.List;

@Service("euCantonService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EuCantonServiceImplImpl extends CrudServiceImpl<EuCanton, Integer> implements EuCantonService {

	/**
	 * 
	 */
	private final EuCantonRepository cantonRepository;

	protected EuCantonServiceImplImpl(EuCantonRepository cantonRepository) {
		super(cantonRepository);
		this.cantonRepository = cantonRepository;
	}

	@Override
	public List<CantonVO> getAll() {
		return cantonRepository.getAllBy();
	}

	@Override
	public List<CantonVO> getAllByPrefecture(Integer id) {
		return cantonRepository.getAllByEuPrefecture_IdPrefecture(id);
	}

	@Override
	public List<CantonVO> getAllByPays(Integer idPays) {
		return cantonRepository.getAllByPays(idPays);
	}

	@Override
	public List<CantonVO> getAllByRegion(Integer idRegion) {
		return cantonRepository.getAllByRegion(idRegion);
	}
}
