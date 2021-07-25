package com.esmc.mcnp.services.acteurs;

import com.esmc.mcnp.model.acteur.EuAvrDetailAchat;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface EuAvrDetailAchatService extends CrudService<EuAvrDetailAchat, Integer> {
    List<EuAvrDetailAchat> findByAvrAchat(Integer idAvrAchat);

    Page<EuAvrDetailAchat> findByAvrAchat(Integer idAvrAchat, Pageable pageable);

    List<EuAvrDetailAchat> findByMembre(String codeMembre);

    Page<EuAvrDetailAchat> findByMembre(String codeMembre, Pageable pageable);

    List<EuAvrDetailAchat> findByReferenceAvr(String reference);

    Page<EuAvrDetailAchat> findByReferenceAvr(String reference, Pageable pageable);
}
