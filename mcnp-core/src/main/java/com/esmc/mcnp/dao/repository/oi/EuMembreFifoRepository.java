package com.esmc.mcnp.dao.repository.oi;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.odd.EuMembreFifo;

public interface EuMembreFifoRepository extends BaseRepository<EuMembreFifo, Long> {
	List<EuMembreFifo> findByValiderAndSubstituerAndAffecter(Integer valider, Integer substituer, Integer affecter);

	@Query("select m from EuMembreFifo m where m.valider = :val and m.substituer = :sub and m.affecter in (:affects)")
	List<EuMembreFifo> findMmebreNonAffecter(@Param("val") Integer valider, @Param("sub") Integer substituer,
			@Param("affects") List<Integer> affecters);
}
