package com.esmc.mcnp.repositories.pc;

import com.esmc.mcnp.model.pc.EuReclamation;
import com.esmc.mcnp.model.pc.TypePassif;
import com.esmc.mcnp.repositories.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EuReclamationRepository extends BaseRepository<EuReclamation, Long> {

    List<EuReclamation> findByTypePassif(TypePassif typePassif);

    Page<EuReclamation> findByTypePassif(TypePassif typePassif, Pageable pageable);

    List<EuReclamation> findByCasPassif_Id(Integer idCas);

    Page<EuReclamation> findByCasPassif_Id(Integer idCas, Pageable pageable);

    List<EuReclamation> findByCodeMembre(String codeMembre);

    Page<EuReclamation> findByCodeMembre(String codeMembre, Pageable pageable);

    List<EuReclamation> findByCodeMembreAndCasPassif_Id(String codeMembre, Integer idCas);

    Page<EuReclamation> findByCodeMembreAndCasPassif_Id(String codeMembre, Integer idCas, Pageable pageable);

    List<EuReclamation> findByCodeMembreAndTypePassif(String codeMembre, TypePassif typePassif);

    Page<EuReclamation> findByCodeMembreAndTypePassif(String codeMembre, TypePassif typePassif, Pageable pageable);

    @Query(value = "select e from EuReclamation e join e.casPassif c where e.typePassif = :type and c.id = :cas")
    Page<EuReclamation> findByPassifAndCas(@Param("type") TypePassif passif, @Param("cas") Integer idCasPassif, Pageable pageable);

    @Query(value = "select e from EuReclamation e join e.casPassif c where e.typePassif = :type and c.id = :cas")
    List<EuReclamation> findByPassifAndCas(@Param("type") TypePassif passif, @Param("cas") Integer idCasPassif);

    @Query(value = "select r from EuReclamation r join r.casPassif c where r.codeMembre = :membre and r.typePassif = :type and c.id = :cas")
    EuReclamation findByMembreAndPassifAndCas(@Param("membre") String codeMembre, @Param("type") TypePassif passif, @Param("cas") Integer idCasPassif);
}
