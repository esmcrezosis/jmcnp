package com.esmc.mcnp.services.odd;

import com.esmc.mcnp.dto.projections.AgencesOddVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.odd.EuAgencesOdd;
import com.esmc.mcnp.repositories.odd.EuAgenceOddRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

import java.util.List;

@Service
public class EuAgenceOddServiceImplImpl extends CrudServiceImpl<EuAgencesOdd, Integer> implements EuAgenceOddService {

	private final EuAgenceOddRepository agenceOddRepository;

	protected EuAgenceOddServiceImplImpl(EuAgenceOddRepository agenceOddRepository) {
		super(agenceOddRepository);
		this.agenceOddRepository = agenceOddRepository;
	}

	@Override
	public List<EuAgencesOdd> findByCentres(Integer id) {
		return agenceOddRepository.findByEuCentre_IdCentres(id);
	}

	@Override
	public EuAgencesOdd findBySource(Boolean source) {
		return agenceOddRepository.findByAgencesOddSource(source);
	}

	@Override
	public Page<EuAgencesOdd> fetchAll(Pageable pageable) {
		return agenceOddRepository.fetchAll(pageable);
	}

	@Override
	public Page<EuAgencesOdd> fetchAll(Long id, Pageable pageable) {
		return agenceOddRepository.fetchAll(id, pageable);
	}

	@Override
	public List<AgencesOddVO> getAll() {
		return agenceOddRepository.getAllBy();
	}
}
