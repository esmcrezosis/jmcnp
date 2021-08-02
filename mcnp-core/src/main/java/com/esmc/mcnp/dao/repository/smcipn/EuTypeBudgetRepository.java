package com.esmc.mcnp.dao.repository.smcipn;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.smcipn.EuTypeBudget;

public interface EuTypeBudgetRepository extends BaseRepository<EuTypeBudget, Long> {
	List<EuTypeBudget> findByUser_IdUtilisateur(Long id);

	Page<EuTypeBudget> findByUser_IdUtilisateur(Long id, Pageable page);
}
