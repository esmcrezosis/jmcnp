package com.esmc.mcnp.infrastructure.services.odd;

import com.esmc.mcnp.dao.repository.odd.EuCentresRepository;
import com.esmc.mcnp.domain.dto.projections.CentreVO;
import com.esmc.mcnp.domain.entity.odd.EuCentres;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EuCentresServiceImplImpl extends CrudServiceImpl<EuCentres, Integer> implements EuCentresService {

	private EuCentresRepository centresRepository;
	
	protected EuCentresServiceImplImpl(EuCentresRepository centresRepository) {
		super(centresRepository);
		this.centresRepository = centresRepository;
	}

	@Override
	public List<CentreVO> getAllVO() {
		return centresRepository.getAllVO();
	}

	@Override
	public List<EuCentres> getAll() {
		return centresRepository.getAllBy();
	}

	@Override
	public Page<EuCentres> getAll(Pageable pageable) {
		return centresRepository.getAllBy(pageable);
	}

	@Override
	public List<EuCentres> findByUser(Long idUser) {
		return centresRepository.findByUser(idUser);
	}

	@Override
	public Page<EuCentres> findByUser(Long idUser, Pageable pageable) {
		return centresRepository.findByUser(idUser, pageable);
	}
}
