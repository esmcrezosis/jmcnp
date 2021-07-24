package com.esmc.mcnp.repositories.obps;

import com.esmc.mcnp.model.obps.EuAncienCreditConsommer;
import com.esmc.mcnp.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EuAncienCreditConsommerRepository extends BaseRepository<EuAncienCreditConsommer, Long> {
    List<EuAncienCreditConsommer> findByIdCredit(Long idCredit);

    @Query("select sum(c.montConsommation) from EuAncienCreditConsommer c where c.idCredit = :idCredit")
    Double sumAncienCreditConsommerByIdCredit(@Param("idCredit") Long idCedit);
}
