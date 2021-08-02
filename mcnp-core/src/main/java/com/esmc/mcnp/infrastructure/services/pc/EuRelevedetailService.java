package com.esmc.mcnp.infrastructure.services.pc;

import com.esmc.mcnp.domain.entity.pc.EuRelevedetail;
import com.esmc.mcnp.infrastructure.services.base.BaseService;
import com.esmc.mcnp.infrastructure.services.base.CrudService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EuRelevedetailService extends CrudService<EuRelevedetail, Integer> {

    Double getDetailSumCapaByReleve(Integer releveId);

    Double getDetailSumCapaByReleve(Integer releveId, String ressource);

    List<EuRelevedetail> findByReleve(Integer releveId);

    List<EuRelevedetail> findByProduit(String produit);

    List<EuRelevedetail> findByReleveAndProduit(Integer releveId, String produit);

    Page<EuRelevedetail> findByReleve(Integer releveId, Pageable pageable);

    Page<EuRelevedetail> findByProduit(String produit, Pageable pageable);

    Page<EuRelevedetail> findByReleveAndProduit(Integer releveId, String produit, Pageable pageable);
}
