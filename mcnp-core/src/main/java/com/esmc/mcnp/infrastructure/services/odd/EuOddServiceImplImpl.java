package com.esmc.mcnp.infrastructure.services.odd;

import com.esmc.mcnp.dao.repository.odd.EuOddRepository;
import com.esmc.mcnp.domain.dto.odd.Odd;
import com.esmc.mcnp.domain.entity.odd.EuOdd;
import com.esmc.mcnp.infrastructure.services.base.CrudServiceImpl;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EuOddServiceImplImpl extends CrudServiceImpl<EuOdd, Integer> implements EuOddService {
	
	private EuOddRepository oddRepository;

	protected EuOddServiceImplImpl(EuOddRepository oddRepository) {
		super(oddRepository);
		this.oddRepository = oddRepository;
	}

	@Override
	public List<Odd> fetchAll() {
		return oddRepository.getAll();
	}
}
