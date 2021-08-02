package com.esmc.mcnp.dao.repository.obps;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obps.EuAncienCreditConsommer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EuAncienCreditConsommerRepository extends BaseRepository<EuAncienCreditConsommer, Long> {
    List<EuAncienCreditConsommer> findByIdCredit(Long idCredit);

    @Query("select sum(c.montConsommation) from EuAncienCreditConsommer c where c.idCredit = :idCredit")
    Double sumAncienCreditConsommerByIdCredit(@Param("idCredit") Long idCedit);
}
