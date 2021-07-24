package com.esmc.mcnp.services.odd;

import com.esmc.mcnp.dto.projections.CentreVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.odd.EuCentres;
import com.esmc.mcnp.repositories.odd.EuCentresRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

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
