package com.esmc.mcnp.dao.repository.pc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.pc.EuReleve;

/**
 * Created by USER on 23/05/2017.
 */
public interface EuReleveRepository extends BaseRepository<EuReleve, Integer> {

    List<EuReleve> findByReleveMembre(String codeMembre);

    List<EuReleve> findByReleveMembreAndPublier(String codeMembre, int publier);

    Page<EuReleve> findByReleveMembre(String codeMembre, Pageable pageable);

    Page<EuReleve> findByReleveMembreAndPublier(String codeMembre, int publier, Pageable pageable);

    List<EuReleve> findByNewCodeMembre(String codeMembre);

    List<EuReleve> findByNewCodeMembreAndPublier(String codeMembre, int publier);

    Page<EuReleve> findByNewCodeMembre(String codeMembre, Pageable pageable);

    Page<EuReleve> findByNewCodeMembreAndPublier(String codeMembre, int publier, Pageable pageable);

    List<EuReleve> findByReleveType(String type);

    Page<EuReleve> findByReleveType(String type, Pageable pageable);

    @Query("select r From EuReleve r where r.releveMembre like :membre and r.releveType like :type")
    List<EuReleve> findByMembreAndType(@Param("membre") String codeMembre, @Param("type") String type);

    @Query("select r From EuReleve r where r.releveMembre like :membre and r.releveType like :type and r.publier = :pub")
    List<EuReleve> findByMembreAndType(@Param("membre") String codeMembre, @Param("type") String type, @Param("pub") int publier);

    @Query("select r From EuReleve r where r.newCodeMembre like :membre and r.releveType like :type")
    List<EuReleve> findByNewMembreAndType(@Param("membre") String newCodeMembre, @Param("type") String type);

    @Query("select r From EuReleve r where r.newCodeMembre like :membre and r.releveType like :type and r.publier = :pub")
    List<EuReleve> findByNewMembreAndType(@Param("membre") String newCodeMembre, @Param("type") String type, @Param("pub") int publier);
}
