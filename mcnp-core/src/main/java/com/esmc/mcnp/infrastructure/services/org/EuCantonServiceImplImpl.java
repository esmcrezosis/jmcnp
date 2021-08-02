package com.esmc.mcnp.infrastructure.services.org;

import com.esmc.mcnp.dao.repository.org.EuCantonRepository;
import com.esmc.mcnp.domain.dto.projections.CantonVO;
import com.esmc.mcnp.domain.entity.org.EuCanton;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
