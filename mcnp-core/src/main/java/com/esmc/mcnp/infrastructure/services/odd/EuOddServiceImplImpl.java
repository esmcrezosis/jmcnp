package com.esmc.mcnp.services.odd;

import com.esmc.mcnp.dto.odd.Odd;
import org.springframework.stereotype.Service;

import com.esmc.mcnp.model.odd.EuOdd;
import com.esmc.mcnp.repositories.odd.EuOddRepository;
import com.esmc.mcnp.services.base.CrudServiceImpl;

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
