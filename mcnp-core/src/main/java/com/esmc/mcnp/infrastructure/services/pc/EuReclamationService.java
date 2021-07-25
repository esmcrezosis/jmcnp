package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.model.pc.EuReclamation;
import com.esmc.mcnp.model.pc.TypePassif;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuReclamationService extends CrudService<EuReclamation, Long> {

    List<EuReclamation> findByTypePassif(TypePassif typePassif);

    List<EuReclamation> findByCasPassif(Integer idCas);

    List<EuReclamation> findByCodeMembre(String codeMembre);

    List<EuReclamation> findByPassifAndCas(TypePassif passif, Integer idCasPassif);

    Page<EuReclamation> findByCodeMembre(String codeMembre, Pageable pageable);

    List<EuReclamation> findByMembreAndCasPassif(String codeMembre, Integer idCas);

    Page<EuReclamation> findByMembreAndCasPassif(String codeMembre, Integer idCas, Pageable pageable);

    List<EuReclamation> findByMembreAndTypePassif(String codeMembre, TypePassif typePassif);

    Page<EuReclamation> findByMembreAndTypePassif(String codeMembre, TypePassif typePassif, Pageable pageable);

    Page<EuReclamation> findByTypePassif(TypePassif typePassif, Pageable pageable);

    Page<EuReclamation> findByCasPassif(Integer idCas, Pageable pageable);

    Page<EuReclamation> findByPassifAndCas(TypePassif passif, Integer idCasPassif, Pageable pageable);

    EuReclamation findByMembreAndPassifAndCas(String codeMembre, TypePassif passif, Integer idCasPassif);
}
