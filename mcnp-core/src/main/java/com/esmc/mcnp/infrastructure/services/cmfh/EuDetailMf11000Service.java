package com.esmc.mcnp.infrastructure.services.cmfh;

import com.esmc.mcnp.domain.entity.cmfh.EuDetailMf11000;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuDetailMf11000Service extends CrudService<EuDetailMf11000, Long> {
    List<EuDetailMf11000> findByMembre(String codeMembre);

    Page<EuDetailMf11000> findByMembre(String codeMembre, Pageable pageable);

    List<EuDetailMf11000> findById(Long idMf11000);

    Page<EuDetailMf11000> findById(Long idMf11000, Pageable pageable);

    EuDetailMf11000 findByMembreAndProprietaire(String codeMembre, Integer prop);
}
