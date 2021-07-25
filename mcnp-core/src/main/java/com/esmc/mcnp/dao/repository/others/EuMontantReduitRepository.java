package com.esmc.mcnp.repositories.others;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.obps.EuMontantReduit;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuMontantReduitRepository extends BaseRepository<EuMontantReduit, Integer> {

	public EuMontantReduit findByEuGcp_IdGcp(Long id);

	@Query("select m from EuMontantReduit m join fetch m.euGcp g where g.idGcp = :id")
	public EuMontantReduit findByGcp(@Param("id") Long idGcp);

}
