package com.esmc.mcnp.repositories.smcipn;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.model.smcipn.EuTypeBudget;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuTypeBudgetRepository extends BaseRepository<EuTypeBudget, Long> {
	List<EuTypeBudget> findByUser_IdUtilisateur(Long id);

	Page<EuTypeBudget> findByUser_IdUtilisateur(Long id, Pageable page);
}
