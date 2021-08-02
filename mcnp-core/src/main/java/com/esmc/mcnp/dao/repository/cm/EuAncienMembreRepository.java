package com.esmc.mcnp.dao.repository.cm;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cm.EuAncienMembre;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EuAncienMembreRepository extends BaseRepository<EuAncienMembre, String> {
    EuAncienMembre findByCodeMembre(String codeMembre);

    @Query(value = "select ancien_code_membre from eu_ancien_membre where code_membre like :code", nativeQuery = true)
    String getAncienCodeByCodeMembre(@Param("code") String codeMembre);
}
