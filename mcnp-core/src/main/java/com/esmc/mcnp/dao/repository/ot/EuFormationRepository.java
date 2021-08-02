package com.esmc.mcnp.dao.repository.ot;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.ot.EuFormation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuFormationRepository extends BaseRepository<EuFormation, Long> {
    List<EuFormation> findByCodeMembre(String codeMembre);

    Page<EuFormation> findByCodeMembre(String codeMembre, Pageable pageable);
}
