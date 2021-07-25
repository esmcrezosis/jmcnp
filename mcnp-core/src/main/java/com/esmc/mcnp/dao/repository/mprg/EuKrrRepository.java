package com.esmc.mcnp.repositories.mprg;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.mprg.EuKrr;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuKrrRepository extends BaseRepository<EuKrr, Long> {

	@Query("select k from EuKrr k join fetch k.euProduit where k.montKrr > k.montReconst and k.idCredit = :id and k.typeKrr like :type")
	public EuKrr findByIdCreditAndTypeKrr(@Param("id") long idCredit, @Param("type") String typeKrr);
}
