package com.esmc.mcnp.services.pc;

import com.esmc.mcnp.model.pc.EuReleve;
import com.esmc.mcnp.services.base.BaseService;
import com.esmc.mcnp.services.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuReleveService extends CrudService<EuReleve, Integer> {

    List<EuReleve> findByReleveMembre(String codeMembre);

    List<EuReleve> findByReleveMembre(String codeMembre, Integer publier);

    Page<EuReleve> findByReleveMembre(String codeMembre, Pageable pageable);

    Page<EuReleve> findByReleveMembre(String codeMembre, Integer publier, Pageable pageable);

    List<EuReleve> findByReleveMembreAndType(String codeMembre, String type);

    List<EuReleve> findByReleveMembreAndType(String codeMembre, String type, Integer publier);

    List<EuReleve> findByMembre(String codeMembre);

    List<EuReleve> findByMembre(String codeMembre, Integer publier);

    Page<EuReleve> findByMembre(String codeMembre, Pageable pageable);

    Page<EuReleve> findByMembre(String codeMembre, Integer publier, Pageable pageable);

    List<EuReleve> findByReleveType(String type);

    Page<EuReleve> findByReleveType(String type, Pageable pageable);

    List<EuReleve> findByMembreAndType(String newCodeMembre, String type);

    List<EuReleve> findByMembreAndType(String newCodeMembre, String type, Integer publier);
    
    Double verifierRelever(String typePassif, String codeMembre, String type, Integer publier);

    Double verifierRelever(EuReleve releve);

    Double verifierRelever(Integer idReleve);
}
