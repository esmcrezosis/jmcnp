package com.esmc.mcnp.dao.repository.pc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.dao.repository.base.BaseRepository;
import com.esmc.mcnp.domain.entity.pc.EuRelevedetail;

/**
 * Created by USER on 23/05/2017.
 */
public interface EuRelevedetailRepository extends BaseRepository<EuRelevedetail, Integer> {

    @Query("select sum(rd.relevedetailMontant) From EuRelevedetail rd where rd.relevedetailReleve = :id")
    Double getDetailSumCapaByReleve(@Param("id") Integer releveId);

    @Query("select sum(rd.relevedetailMontant) From EuRelevedetail rd where rd.relevedetailReleve = :id and rd.relevedetailProduit like :produit")
    Double getDetailSumCapaByReleve(@Param("id") Integer releveId, @Param("produit") String ressource);

    List<EuRelevedetail> findByRelevedetailReleve(int releveId);

    List<EuRelevedetail> findByRelevedetailProduit(String produit);

    @Query("select rd From EuRelevedetail rd where rd.relevedetailReleve = :rid and rd.relevedetailProduit like :produit")
    List<EuRelevedetail> findAllByReleveAndProduit(@Param("rid") Integer releveId, @Param("produit") String produit);

    Page<EuRelevedetail> findByRelevedetailReleve(int releveId, Pageable pageable);

    Page<EuRelevedetail> findByRelevedetailProduit(String produit, Pageable pageable);

    @Query("select rd From EuRelevedetail rd where rd.relevedetailReleve = :rid and rd.relevedetailProduit like :produit")
    Page<EuRelevedetail> findAllByReleveAndProduit(@Param("rid") Integer releveId, @Param("produit") String produit, Pageable pageable);
}
