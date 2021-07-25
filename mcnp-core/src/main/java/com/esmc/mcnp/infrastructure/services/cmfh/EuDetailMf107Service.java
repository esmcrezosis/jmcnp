package com.esmc.mcnp.services.cmfh;

import com.esmc.mcnp.model.cmfh.EuDetailMf107;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuDetailMf107Service extends CrudService<EuDetailMf107, Long> {
    Page<EuDetailMf107> findByCodeMembre(String codeMembre, Pageable pageable);

    List<EuDetailMf107> findByIdMf107(Integer idMf107);

    Page<EuDetailMf107> findByIdMf107(Integer idMf107, Pageable pageable);

    List<EuDetailMf107> findByNumident(String numident);

    Page<EuDetailMf107> findByNumident(String numident, Pageable pageable);
}
