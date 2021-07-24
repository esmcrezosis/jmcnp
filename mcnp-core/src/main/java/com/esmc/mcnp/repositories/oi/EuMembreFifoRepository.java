package com.esmc.mcnp.repositories.oi;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.odd.EuMembreFifo;
import com.esmc.mcnp.repositories.base.BaseRepository;

public interface EuMembreFifoRepository extends BaseRepository<EuMembreFifo, Long> {
	List<EuMembreFifo> findByValiderAndSubstituerAndAffecter(Integer valider, Integer substituer, Integer affecter);

	@Query("select m from EuMembreFifo m where m.valider = :val and m.substituer = :sub and m.affecter in (:affects)")
	List<EuMembreFifo> findMmebreNonAffecter(@Param("val") Integer valider, @Param("sub") Integer substituer,
			@Param("affects") List<Integer> affecters);
}
