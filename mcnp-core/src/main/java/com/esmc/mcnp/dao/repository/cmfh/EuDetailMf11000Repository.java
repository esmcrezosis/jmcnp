package com.esmc.mcnp.dao.repository.cmfh;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.cmfh.EuDetailMf11000;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuDetailMf11000Repository extends BaseRepository<EuDetailMf11000, Long> {
    List<EuDetailMf11000> findByCodeMembre(String codeMembre);

    Page<EuDetailMf11000> findByCodeMembre(String codeMembre, Pageable pageable);

    List<EuDetailMf11000> findByIdMf11000(Long idMf11000);

    Page<EuDetailMf11000> findByIdMf11000(Long idMf11000, Pageable pageable);

    EuDetailMf11000 findByCodeMembreAndProprietaire(String codeMembre, Integer prop);
}
