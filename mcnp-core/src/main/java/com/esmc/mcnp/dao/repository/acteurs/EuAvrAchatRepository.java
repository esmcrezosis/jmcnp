package com.esmc.mcnp.dao.repository.acteurs;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.acteur.EuAvrAchat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuAvrAchatRepository extends BaseRepository<EuAvrAchat, Integer> {

    List<EuAvrAchat> findByCodeMembreAcheteur(String codeMembre);

    Page<EuAvrAchat> findByCodeMembreAcheteur(String codeMembre, Pageable pageable);

    EuAvrAchat findByReferenceAvr(String reference);
}
