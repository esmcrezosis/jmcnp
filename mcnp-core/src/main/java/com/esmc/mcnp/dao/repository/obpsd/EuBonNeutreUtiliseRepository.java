package com.esmc.mcnp.dao.repository.obpsd;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.obpsd.EuBonNeutreUtilise;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EuBonNeutreUtiliseRepository extends BaseRepository<EuBonNeutreUtilise, Integer> {

    List<EuBonNeutreUtilise> findByEuBonNeutre_bonNeutreCode(String code);

    List<EuBonNeutreUtilise> findByEuBonNeutre_bonNeutreCodeMembre(String codeMembre);

    Page<EuBonNeutreUtilise> findByEuBonNeutre_bonNeutreCode(String code, Pageable pageable);

    Page<EuBonNeutreUtilise> findByEuBonNeutre_bonNeutreCodeMembre(String codeMembre, Pageable pageable);

    Page<EuBonNeutreUtilise> findByBonNeutreDetailId(Integer id, Pageable pageable);

    Page<EuBonNeutreUtilise> findByEuBonNeutre_BonNeutreId(Integer id, Pageable pageable);

    Page<EuBonNeutreUtilise> findByBonNeutreUtiliseDateBetween(Date deb, Date fin, Pageable pageable);

    Page<EuBonNeutreUtilise> findByBonNeutreUtiliseDateBefore(Date date, Pageable pageable);

    Page<EuBonNeutreUtilise> findByBonNeutreUtiliseDateAfter(Date date, Pageable pageable);

    @Query("select u from EuBonNeutreUtilise u where u.euBonNeutre.bonNeutreCodeMembre like :membre and u.bonNeutreUtiliseDate >= :date")
    Page<EuBonNeutreUtilise> findByMembreAndDateSup(@Param("membre") String codeMembre, @Param("date") Date date, Pageable pageable);

	@Query("select u from EuBonNeutreUtilise u where u.euBonNeutre.bonNeutreCodeMembre like :membre and u.bonNeutreUtiliseDate <= :date")
	Page<EuBonNeutreUtilise> findByMembreAndDateInf(@Param("membre") String codeMembre, @Param("date") Date date, Pageable pageable);

    @Query("select max(b.bonNeutreUtiliseId) from EuBonNeutreUtilise b")
    int getLastInsertedId();
}
