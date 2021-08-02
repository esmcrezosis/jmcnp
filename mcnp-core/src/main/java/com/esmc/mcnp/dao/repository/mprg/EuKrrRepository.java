package com.esmc.mcnp.dao.repository.mprg;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.mprg.EuKrr;

public interface EuKrrRepository extends BaseRepository<EuKrr, Long> {

	@Query("select k from EuKrr k join fetch k.euProduit where k.montKrr > k.montReconst and k.idCredit = :id and k.typeKrr like :type")
	public EuKrr findByIdCreditAndTypeKrr(@Param("id") long idCredit, @Param("type") String typeKrr);
}
