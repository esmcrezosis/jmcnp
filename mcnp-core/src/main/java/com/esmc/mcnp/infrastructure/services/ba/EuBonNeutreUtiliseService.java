package com.esmc.mcnp.services.ba;

import com.esmc.mcnp.model.obpsd.EuBonNeutreUtilise;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface EuBonNeutreUtiliseService extends CrudService<EuBonNeutreUtilise, Integer> {
    public int getLastInsertedId();

    Page<EuBonNeutreUtilise> findByBonNeutreCode(String code, Pageable pageable);

    Page<EuBonNeutreUtilise> findByBonNeutreCodeMembre(String codeMembre, Pageable pageable);

    Page<EuBonNeutreUtilise> findByBonNeutreDetailId(Integer id, Pageable pageable);

    Page<EuBonNeutreUtilise> findByBonNeutreId(Integer id, Pageable pageable);

    Page<EuBonNeutreUtilise> findByBonNeutreUtiliseDateBetween(Date deb, Date fin, Pageable pageable);

    Page<EuBonNeutreUtilise> findByBonNeutreUtiliseDateBefore(Date date, Pageable pageable);

    Page<EuBonNeutreUtilise> findByBonNeutreUtiliseDateAfter(Date date, Pageable pageable);

    Page<EuBonNeutreUtilise> findByMembreAndDateSup(String codeMembre, Date date, Pageable pageable);

    Page<EuBonNeutreUtilise> findByMembreAndDateInf(String codeMembre, Date date, Pageable pageable);
}
