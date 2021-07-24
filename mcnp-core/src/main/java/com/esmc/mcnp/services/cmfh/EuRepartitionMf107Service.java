package com.esmc.mcnp.services.cmfh;

import com.esmc.mcnp.model.cmfh.EuRepartitionMf107;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuRepartitionMf107Service extends CrudService<EuRepartitionMf107, Long> {
    List<EuRepartitionMf107> findByCodeMembre(String codeMembre);

    List<EuRepartitionMf107> findByIdMf107(Long idMf107);

    Page<EuRepartitionMf107> findByCodeMembre(String codeMembre, Pageable pageable);

    Page<EuRepartitionMf107> findByIdMf107(Long idMf107, Pageable pageable);
}
