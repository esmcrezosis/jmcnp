package com.esmc.mcnp.infrastructure.services.cmfh;

import com.esmc.mcnp.domain.entity.cmfh.EuRepartitionMf11000;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuRepartitionMf11000Service extends CrudService<EuRepartitionMf11000, Long> {
    List<EuRepartitionMf11000> findByCodeMembre(String codeMembre);

    List<EuRepartitionMf11000> findByCodeMf11000(String codeMf11000);

    List<EuRepartitionMf11000> findByIdMf11000(Long idMf11000);

    Page<EuRepartitionMf11000> findByCodeMembre(String codeMembre, Pageable pageable);

    Page<EuRepartitionMf11000> findByCodeMf11000(String codeMf11000, Pageable pageable);

    Page<EuRepartitionMf11000> findByIdMf11000(Long idMf11000, Pageable pageable);
}
