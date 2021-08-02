package com.esmc.mcnp.infrastructure.services.acteurs;

import com.esmc.mcnp.domain.entity.acteur.EuAvrAchat;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuAvrAchatService extends CrudService<EuAvrAchat, Integer> {
    List<EuAvrAchat> findByMembre(String codeMembre);
    Page<EuAvrAchat> findByMembre(String codeMembre, Pageable pageable);
    EuAvrAchat findByReference(String reference);
}
