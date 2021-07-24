/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esmc.mcnp.repositories.ba;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.esmc.mcnp.model.ba.EuCapa;
import com.esmc.mcnp.repositories.base.BaseRepository;

/**
 * @author USER
 */
public interface EuCapaRepository extends BaseRepository<EuCapa, String> {

    List<EuCapa> findByCodeMembre(String codeMembre);

    @Query("select distinct c.codeProduit from EuCapa c where c.codeMembre like :membre and c.origineCapa like :origine")
    List<String> findProduitByMembreAndOrigine(@Param("membre") String codeMembre,
                                               @Param("origine") String origine);

    @Query("select c from EuCapa c where c.montantSolde > 0 and c.codeMembre like :membre")
    List<EuCapa> fetchByCodeMembre(@Param("membre") String codeMembre);

    @Query("select c from EuCapa c where c.montantSolde > 0 and c.codeMembre like :membre and c.origineCapa like :origine")
    List<EuCapa> findByCodeMembreAndOrigineCapa(@Param("membre") String codeMembre,
                                                @Param("origine") String origineCapa);

    List<EuCapa> findByEuCompte_CodeCompte(String codeCompte);

    @EntityGraph(attributePaths = {"euCompte"})
    Page<EuCapa> findByCodeMembre(String codeMembre, Pageable pageable);

    @Query(value = "select c from EuCapa c left join fetch c.euCompte where c.montantSolde > 0 and c.codeMembre like :membre and c.origineCapa like :origine",
            countQuery = "select c from EuCapa c left join c.euCompte where c.codeMembre like :membre and c.origineCapa like :origine")
    Page<EuCapa> findByCodeMembreAndOrigineCapa(@Param("membre") String codeMembre,
                                                @Param("origine") String origineCapa, Pageable pageable);

    @EntityGraph(attributePaths = {"euCompte"})
    Page<EuCapa> findByEuCompte_CodeCompte(String codeCompte, Pageable pageable);

    @EntityGraph(attributePaths = {"euCompte"})
    Page<EuCapa> findByEuCompte_CodeCompteAndOrigineCapa(String codeCompte, String origine, Pageable pageable);

    @Query("select cp from EuCapa cp where cp.idOperation = :op")
    EuCapa findCapaByIdOperation(@Param("op") BigDecimal idOperation);

    @Query("select distinct cp.codeProduit from EuCapa cp join cp.euCompte c where c.codeCompte = :compte and cp.montantSolde > 0")
    List<String> findCodeProduitByCodeCompte(@Param("compte") String codeCompte);

    @Query("select cp from EuCapa cp join cp.euCompte c where c.codeCompte = :compte and cp.montantSolde > 0 and cp.codeProduit = :produit")
    List<EuCapa> findCapaByProduit(@Param("compte") String codeCompte, @Param("produit") String codeProduit);
    
    @Query("select cp from EuCapa cp where cp.montantSolde > 0 and cp.typeCapa = :type")
    List<EuCapa> findByType(@Param("type") String typeCapa);
    
    @Query("select cp from EuCapa cp where cp.codeMembre = :membre and cp.montantSolde > 0 and cp.typeCapa = :type")
    List<EuCapa> findByType(@Param("membre") String codeMembre, @Param("type") String typeCapa);
    
    @Query("select cp from EuCapa cp join cp.euCompte c where c.codeCompte = :compte and cp.montantSolde > 0 and cp.typeCapa = :type")
    List<EuCapa> findByCompteAndType(@Param("compte") String codeCompte, @Param("type") String typeCapa);

    @Query("select cp from EuCapa cp where cp.montantSolde > 0 and cp.codeMembre = :membre and cp.typeCapa = :type order by cp.origineCapa")
    List<EuCapa> findByMembreAndType(@Param("membre") String codeMembre, @Param("type") String typeCapa);

    @Query("select cp from EuCapa cp where cp.codeMembre = :membre and cp.montantSolde > 0 and cp.typeCapa = :type and cp.origineCapa like :origine order by cp.origineCapa")
    List<EuCapa> findByMembreAndTypeAndOrigine(@Param("membre") String codeMembre,
                                                  @Param("type") String typeCapa, @Param("origine") String origine);
    
    @Query("select cp from EuCapa cp where cp.codeMembre = :membre and cp.montantSolde > 0 and cp.codeProduit = :produit and cp.origineCapa like :origine order by cp.origineCapa")
    List<EuCapa> findByMembreAndProduitAndOrigine(@Param("membre") String codeMembre,
                                                  @Param("produit") String codeProduit, @Param("origine") String origine);
    
    @Query("select sum(cp.montantCapa) from EuCapa cp join cp.euCompte c where c.codeCompte = :compte and cp.codeProduit= :produit and cp.montantSolde > 0")
    Double getSommeByCompteAndProduit(@Param("compte") String codeCompte, @Param("produit") String codeProduit);

    @Query("select cp from EuCapa cp join fetch cp.euCompte c where cp.codeMembre = :membre and cp.codeProduit= :produit and cp.montantSolde > 0")
    List<EuCapa> findByMembreAndProduit(@Param("membre") String codeMembre,
                                        @Param("produit") String codeProduit);

    @Query("select cp From EuCapa cp join fetch cp.euCompte c where cp.codeMembre like :membre and cp.codeProduit= :produit and cp.origineCapa in :origines order by cp.origineCapa")
    List<EuCapa> findByMembreAndOrigine(@Param("membre") String codeMembre, @Param("produit") String produit,
                                        @Param("origines") List<String> origines);

    @Query("select cp From EuCapa cp join fetch cp.euCompte c where cp.codeMembre like :membre and cp.origineCapa in :origines order by cp.origineCapa")
    List<EuCapa> findByMembreAndOrigine(@Param("membre") String codeMembre,
                                        @Param("origines") List<String> origines);

    @Query("select sum(cp.montantSolde) From EuCapa cp where cp.codeMembre like :membre and cp.origineCapa like :origine")
    double findSumCapaByMembreAndOrigine(@Param("membre") String codeMembre, @Param("origine") String origine);

    @Query("select sum(cp.montantSolde) From EuCapa cp where cp.codeMembre like :membre and cp.origineCapa in :origines")
    double findSumCapaByMembreAndOrigine(@Param("membre") String codeMembre,
                                         @Param("origines") List<String> origines);
}
