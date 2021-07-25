package com.esmc.mcnp.repositories.acteurs;

import com.esmc.mcnp.model.acteur.EuAvrAchat;
import com.esmc.mcnp.repositories.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuAvrAchatRepository extends BaseRepository<EuAvrAchat, Integer> {

    List<EuAvrAchat> findByCodeMembreAcheteur(String codeMembre);

    Page<EuAvrAchat> findByCodeMembreAcheteur(String codeMembre, Pageable pageable);

    EuAvrAchat findByReferenceAvr(String reference);
}
