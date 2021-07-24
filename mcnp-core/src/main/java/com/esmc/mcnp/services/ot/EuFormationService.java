package com.esmc.mcnp.services.ot;

import com.esmc.mcnp.model.ot.EuFormation;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuFormationService extends CrudService<EuFormation, Long> {
    List<EuFormation> findByMembre(String codeMembre);

    Page<EuFormation> findByMembre(String codeMembre, Pageable pageable);
}
