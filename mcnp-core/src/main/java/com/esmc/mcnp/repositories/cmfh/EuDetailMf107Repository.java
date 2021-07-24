package com.esmc.mcnp.repositories.cmfh;

import java.util.List;

import com.esmc.mcnp.model.cmfh.EuDetailMf107;
import com.esmc.mcnp.repositories.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EuDetailMf107Repository extends BaseRepository<EuDetailMf107, Long> {

    List<EuDetailMf107> findByCodeMembre(String codeMembre);

    Page<EuDetailMf107> findByCodeMembre(String codeMembre, Pageable pageable);

    List<EuDetailMf107> findByIdMf107(Integer idMf107);

    Page<EuDetailMf107> findByIdMf107(Integer idMf107, Pageable pageable);

    List<EuDetailMf107> findByNumident(String numident);

    Page<EuDetailMf107> findByNumident(String numident, Pageable pageable);

    EuDetailMf107 findByCodeMembreAndProprietaire(String codeMembre, Integer prop);
}
