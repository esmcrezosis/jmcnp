package com.esmc.mcnp.repositories.ot;

import com.esmc.mcnp.model.ot.EuFormation;
import com.esmc.mcnp.repositories.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuFormationRepository extends BaseRepository<EuFormation, Long> {
    List<EuFormation> findByCodeMembre(String codeMembre);

    Page<EuFormation> findByCodeMembre(String codeMembre, Pageable pageable);
}
